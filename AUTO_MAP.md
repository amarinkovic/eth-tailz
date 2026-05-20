# EventResolver refactor: map-of-handlers + Mirror-based auto-derivation

## Before

`EventResolverImpl.getTypedEvent` was a ~290-line `match` over ~45 event names. Every branch repeated the same shape:

```scala
case "OrderAdded" =>
  OrderAdded(
    orderId           = NaymsDiamond.getOrderAddedEventFromLog(obj).orderId,
    maker             = NaymsDiamond.getOrderAddedEventFromLog(obj).maker,
    sellToken         = NaymsDiamond.getOrderAddedEventFromLog(obj).sellToken,
    sellAmount        = NaymsDiamond.getOrderAddedEventFromLog(obj).sellAmount,
    sellAmountInitial = NaymsDiamond.getOrderAddedEventFromLog(obj).sellAmountInitial,
    buyToken          = NaymsDiamond.getOrderAddedEventFromLog(obj).buyToken,
    buyAmount         = NaymsDiamond.getOrderAddedEventFromLog(obj).buyAmount,
    buyAmountInitial  = NaymsDiamond.getOrderAddedEventFromLog(obj).buyAmountInitial,
    state             = NaymsDiamond.getOrderAddedEventFromLog(obj).state,
  )
```

Two kinds of duplication compounded:
1. **Dispatch boilerplate** — `case "X" => ... case _ => Unsupported(name)`.
2. **Per-event constructor boilerplate** — for most events, the response and the case class share field names, so each branch is just "for every field, copy it".

## After

Two layers of cleanup:

### #1 — Map-of-handlers + `custom` helper

Dispatch becomes a table lookup:

```scala
private val resolvers: Map[String, LogObject => TypedEvent] = Map(...)

def getTypedEvent(obj: LogObject): Task[TypedEvent] = ZIO.attempt {
  val name = getName(obj.getTopics.get(0))
  resolvers.get(name).fold[TypedEvent](Unsupported(name))(_(obj))
}
```

For events where field names need rewriting, a `custom` helper keeps registrations compact:

```scala
private def custom[E, T <: TypedEvent](get: LogObject => E)(build: E => T): LogObject => TypedEvent =
  obj => build(get(obj))

// usage:
"TokenWrapped" -> custom(getTokenWrappedEventFromLog)(e =>
  TokenWrapped(id = e.entityId, wrapper = e.tokenWrapper))
```

This alone removes the `case "X" =>` ceremony and the `case _ => Unsupported` tail.

### #2 — Mirror-based auto-derivation via `AutoMap`

Most events (36 of ~45) have response fields that match the case class field names 1:1, with only **type** differences — `Bytes32 → Bytes32String`, `Uint256 → BigInt`, etc. — already covered by `given Conversion[...]` instances at the top of `EventResolver.scala`.

For these, we don't want to write a constructor at all. We want this:

```scala
"OrderAdded" -> auto[OrderAdded](getOrderAddedEventFromLog),
```

That's what `AutoMap.scala` enables.

## What `AutoMap.scala` does

`AutoMap.from[E, T](e)` is an `inline def` that expands, at compile time, into an explicit constructor call building a `T` from an `E`.

```scala
inline def from[E, T <: Product](e: E)(using m: Mirror.ProductOf[T]): T =
  ${ fromImpl[E, T]('e, 'm) }
```

The `${ ... }` is a Scala 3 macro splice: at every call site the compiler runs `fromImpl`, which inspects `T` and `E` via `quotes.reflect` and emits the constructor code as if it had been hand-written. No reflection happens at runtime — important because the project targets GraalVM native-image, which is hostile to reflective access.

### The macro, step by step

```scala
private def fromImpl[E: Type, T <: Product: Type](
    e: Expr[E],
    m: Expr[Mirror.ProductOf[T]]
)(using Quotes): Expr[T] = {
  import quotes.reflect.*

  val tRepr = TypeRepr.of[T]
  val eRepr = TypeRepr.of[E]
  val targetFields = tRepr.typeSymbol.caseFields
  ...
}
```

1. **List the target's case fields.** `tRepr.typeSymbol.caseFields` returns the declared parameters of `T` in declaration order — exactly what `Mirror.ProductOf[T].fromProduct` expects in tuple form.

2. **For each target field, find a same-named field on the source.**
   ```scala
   val eField = eRepr.typeSymbol.fieldMember(name)
   if eField == Symbol.noSymbol then
     report.errorAndAbort(s"${eRepr.show} has no public field named '$name' ...")
   ```
   The source type `E` is a Java POJO (web3j-generated `*EventResponse` extends `BaseEventResponse` with public fields). `fieldMember(name)` finds the matching field. If it's missing, the macro **fails at compile time** with a clear error pointing at the bad event.

3. **Decide whether a `Conversion` is needed.**
   ```scala
   if eFieldType =:= tFieldType then access  // direct: e.fieldName
   else
     // need Conversion[eFieldType, tFieldType]
     val convT = TypeRepr.of[Conversion].appliedTo(List(eFieldType, tFieldType))
     Implicits.search(convT) match
       case s: ImplicitSearchSuccess =>
         Apply(Select.unique(s.tree, "apply"), List(access))   // conv(e.fieldName)
       case _ =>
         report.errorAndAbort(s"No given Conversion[...] for field '$name' ...")
   ```
   - Types equal? Emit a raw field access: `e.fieldName`.
   - Types differ? Look up an in-scope `given Conversion[FromType, ToType]` and emit `conv.apply(e.fieldName)`. The `given Conversion[Bytes32, Bytes32String]`, `Conversion[Uint256, BigInt]`, etc. defined at the top of `EventResolver.scala` are picked up automatically.
   - No conversion found? **Compile error**, naming the field and the missing conversion.

4. **Build the case class via the product mirror.**
   ```scala
   val tupleExpr = Expr.ofTupleFromSeq(argTerms.map(_.asExpr))
   '{ $m.fromProduct($tupleExpr) }
   ```
   The list of per-field expressions is bundled into a `Tuple` and fed through `Mirror.ProductOf[T].fromProduct`. Because all checks happened at compile time, the runtime code is just: read field, optionally apply a conversion lambda, construct the product.

### Why `Mirror.ProductOf[T]` and not `Mirror.ProductOf[E]`

`T` is always a Scala case class (auto-derivable). `E` is a Java POJO — no `Mirror` available. Asymmetric design: we drive the iteration from `T`'s fields and look them up on `E`, not the other way around.

### The `AutoBuilder` indirection

`AutoMap.from` needs **both** type parameters supplied. In the resolver registry we want to pass `E` *implicitly* via the getter's return type and `T` *explicitly* (it tells the macro which case class to build):

```scala
private class AutoBuilder[T <: TypedEvent & Product] {
  inline def apply[E](get: LogObject => E)(using m: Mirror.ProductOf[T]): LogObject => TypedEvent =
    obj => AutoMap.from[E, T](get(obj))
}
private inline def auto[T <: TypedEvent & Product]: AutoBuilder[T] = new AutoBuilder[T]
```

This is the standard Scala 3 trick for **partial type-parameter application**: `auto[OrderAdded](getter)` fixes `T = OrderAdded` and lets Scala infer `E` from the getter.

## Resulting shape

```scala
private val resolvers: Map[String, LogObject => TypedEvent] = Map(
  // 36 trivial 1:1 cases — auto-derived
  "CollateralRatioUpdated" -> auto[CollateralRatioUpdated](getCollateralRatioUpdatedEventFromLog),
  "OrderAdded"             -> auto[OrderAdded](getOrderAddedEventFromLog),
  ...

  // 9 cases with field renames — explicit but still one-line-ish
  "RoleUpdated" -> custom(getRoleUpdatedEventFromLog)(e =>
    RoleUpdated(objectId = e.objectId, contextId = e.contextId,
                roleId = e.assignedRoleId, funcName = e.functionName)),

  // 1 case with extra logic (zip + map)
  "FeeScheduleAdded" -> custom(getFeeScheduleAddedEventFromLog) { e => ... }
)
```

~290 lines of `match` → ~65 lines of registry.

## What we get beyond fewer lines

- **Adding an event becomes one line.** Define the case class with matching field names, register `"EventName" -> auto[EventName](getEventNameEventFromLog)`. Done.
- **Compile-time safety.** If a response field is missing or its type can't be converted, you get a precise error at the registration site — not a runtime `NoSuchFieldError` or silently-wrong data.
- **No runtime reflection.** The macro emits the exact same direct field access + conversion call you'd write by hand. Native-image friendly.
- **The handful of renames stay explicit**, which is correct: a rename is a semantic mapping decision that *should* be visible in source. Auto-derivation is for the trivial cases where there is no decision.
