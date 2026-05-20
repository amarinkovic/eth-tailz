package io.mankea.eth.streamer.service

import scala.deriving.Mirror
import scala.quoted.*

object AutoMap {

  inline def from[E, T <: Product](e: E)(using m: Mirror.ProductOf[T]): T =
    ${ fromImpl[E, T]('e, 'm) }

  private def fromImpl[E: Type, T <: Product: Type](
      e: Expr[E],
      m: Expr[Mirror.ProductOf[T]]
  )(using Quotes): Expr[T] = {
    import quotes.reflect.*

    val tRepr = TypeRepr.of[T]
    val eRepr = TypeRepr.of[E]
    val targetFields = tRepr.typeSymbol.caseFields

    val argTerms: List[Term] = targetFields.map { tf =>
      val name = tf.name
      val tFieldType = tRepr.memberType(tf)
      val eField = eRepr.typeSymbol.fieldMember(name)
      if eField == Symbol.noSymbol then
        report.errorAndAbort(
          s"${eRepr.show} has no public field named '$name' (required for ${tRepr.show})"
        )
      val eFieldType = eRepr.memberType(eField)
      val access = Select(e.asTerm, eField)
      if eFieldType =:= tFieldType then access
      else
        val convT = TypeRepr.of[Conversion].appliedTo(List(eFieldType, tFieldType))
        Implicits.search(convT) match
          case s: ImplicitSearchSuccess =>
            Apply(Select.unique(s.tree, "apply"), List(access))
          case _ =>
            report.errorAndAbort(
              s"No given Conversion[${eFieldType.show}, ${tFieldType.show}] for field '$name' of ${tRepr.show}"
            )
    }

    val tupleExpr = Expr.ofTupleFromSeq(argTerms.map(_.asExpr))
    '{ $m.fromProduct($tupleExpr) }
  }
}
