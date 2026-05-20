package io.mankea.eth.streamer.service

import org.web3j.protocol.core.methods.response.EthLog.LogObject

import scala.quoted.*

/** Mix in to opt a case class out of [[AutoMap.deriveResolvers]]. */
trait NoAutoDerivation

object AutoMap {

  inline def from[E, T <: Product](e: E): T = ${ fromImpl[E, T]('e) }

  /** Emit a `Map[String, LogObject => Sum]` covering every case-class child of the sealed
    * type `Sum` whose name matches a `get<Name>EventFromLog` method on `Owner`. Case classes
    * mixed with [[NoAutoDerivation]] are skipped. Field mapping uses [[from]] semantics
    * (name-match + summoned `given Conversion`). */
  inline def deriveResolvers[Sum, Owner]: Map[String, LogObject => Sum] =
    ${ deriveResolversImpl[Sum, Owner] }

  private def fromImpl[E: Type, T <: Product: Type](e: Expr[E])(using Quotes): Expr[T] = {
    import quotes.reflect.*
    buildProduct(TypeRepr.of[T], TypeRepr.of[E], e.asTerm).asExprOf[T]
  }

  private def deriveResolversImpl[Sum: Type, Owner: Type](using Quotes): Expr[Map[String, LogObject => Sum]] = {
    import quotes.reflect.*

    val sumRepr   = TypeRepr.of[Sum]
    val ownerType = TypeRepr.of[Owner]
    val ownerSym  = ownerType.typeSymbol
    val ownerMod  = ownerSym.companionModule
    val noAuto    = TypeRepr.of[NoAutoDerivation]

    def findGetter(name: String): List[Symbol] = {
      val onClass = ownerSym.methodMember(name)
      if onClass.nonEmpty then onClass
      else if ownerMod != Symbol.noSymbol then ownerMod.methodMember(name)
      else Nil
    }

    val entries: List[Expr[(String, LogObject => Sum)]] =
      sumRepr.typeSymbol.children.flatMap { childSym =>
        val childRepr = childSym.typeRef
        if childRepr <:< noAuto then None
        else if !childSym.flags.is(Flags.Case) then None
        else {
          val caseName   = childSym.name
          val getterName = s"get${caseName}EventFromLog"
          val candidates = findGetter(getterName)
          if candidates.isEmpty then
            report.errorAndAbort(
              s"AutoMap.deriveResolvers: ${ownerSym.fullName} has no method '$getterName' " +
                s"(required for ${childSym.fullName}). Add the method on the owner, or mix " +
                s"`NoAutoDerivation` into ${childSym.name}."
            )
          val getterSym  = candidates.head
          val getterType = if getterSym.owner == ownerSym then ownerType.memberType(getterSym)
                           else ownerMod.termRef.memberType(getterSym)

          val responseRepr = getterType match {
            case mt: MethodType => mt.resType
            case other          => report.errorAndAbort(s"Unexpected signature for $getterName: ${other.show}")
          }

          val lambda: Expr[LogObject => Sum] = Lambda(
            owner = Symbol.spliceOwner,
            tpe   = MethodType(List("obj"))(_ => List(TypeRepr.of[LogObject]), _ => sumRepr),
            rhsFn = (sym, params) => {
              val objTerm    = params.head.asInstanceOf[Term]
              val getterCall = Apply(Ref(getterSym), List(objTerm))
              buildProduct(childRepr, responseRepr, getterCall).changeOwner(sym)
            }
          ).asExprOf[LogObject => Sum]

          Some('{ ${ Expr(caseName) } -> $lambda })
        }
      }

    '{ Map(${ Varargs(entries) }*) }
  }

  private def buildProduct(using Quotes)(
      target: quotes.reflect.TypeRepr,
      source: quotes.reflect.TypeRepr,
      sourceTerm: quotes.reflect.Term
  ): quotes.reflect.Term = {
    import quotes.reflect.*
    val targetFields = target.typeSymbol.caseFields
    val args: List[Term] = targetFields.map { tf =>
      val name       = tf.name
      val tFieldType = target.memberType(tf)
      val eField     = source.typeSymbol.fieldMember(name)
      if eField == Symbol.noSymbol then
        report.errorAndAbort(
          s"${source.show} has no public field named '$name' (required for ${target.show})"
        )
      val eFieldType = source.memberType(eField)
      val access     = Select(sourceTerm, eField)
      if eFieldType =:= tFieldType then access
      else
        val convT = TypeRepr.of[Conversion].appliedTo(List(eFieldType, tFieldType))
        Implicits.search(convT) match
          case s: ImplicitSearchSuccess =>
            Apply(Select.unique(s.tree, "apply"), List(access))
          case _ =>
            report.errorAndAbort(
              s"No given Conversion[${eFieldType.show}, ${tFieldType.show}] for field '$name' of ${target.show}"
            )
    }
    val ctor = target.typeSymbol.primaryConstructor
    New(Inferred(target)).select(ctor).appliedToArgs(args)
  }
}
