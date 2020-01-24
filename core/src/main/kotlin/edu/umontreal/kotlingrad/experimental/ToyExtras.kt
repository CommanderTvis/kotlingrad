package edu.umontreal.kotlingrad.experimental

import ch.obermuhlner.math.big.BigDecimalMath
import java.math.BigDecimal
import java.math.MathContext

class BDReal(number: Number, val sigFigs: Int = 30): RealNumber<BDReal, BigDecimal>(when {
  number is BigDecimal -> number
  number.toDouble().isNaN() -> BigDecimal.ZERO
  1E30 < number.toDouble() -> BigDecimal(1E30)
  -1E30 > number.toDouble() -> BigDecimal(1E30)
  else -> BigDecimal(number.toDouble() + 0.0)
}) {
  val mc = MathContext(sigFigs)
  override fun wrap(value: Number) = BDReal(value, sigFigs)

  override fun sin() = BDReal(BigDecimalMath.sin(value, mc))
  override fun cos() = BDReal(BigDecimalMath.cos(value, mc))
  override fun tan() = BDReal(BigDecimalMath.tan(value, mc))
  override fun ln() = BDReal(BigDecimalMath.log(value, mc))
  override fun sqrt() = BDReal(BigDecimalMath.sqrt(value, mc))
  override fun unaryMinus() = BDReal(-value)

  override fun plus(addend: SFun<BDReal>) = when (addend) {
    is BDReal -> BDReal(value + addend.value)
    else -> super.plus(addend)
  }
  override fun times(multiplicand: SFun<BDReal>) = when (multiplicand) {
    is BDReal -> BDReal(value * multiplicand.value)
    else -> super.times(multiplicand)
  }
  override fun pow(exp: SFun<BDReal>) = when(exp) {
    is BDReal -> BDReal(BigDecimalMath.pow(value, exp.value, mc))
    else -> super.pow(exp)
  }
}