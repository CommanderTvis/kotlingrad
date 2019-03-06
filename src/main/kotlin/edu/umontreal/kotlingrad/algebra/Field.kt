package edu.umontreal.kotlingrad.algebra

interface Field<X: Field<X>>: CommutativeRing<X> {
  val e: X

  fun inverse(): X

  infix operator fun div(divisor: X): X = this * divisor.inverse()

  infix fun pow(exp: X): X

  fun sin(): X

  fun cos(): X

  fun tan(): X

  fun exp(): X

  fun log(): X

  fun sqrt(): X
}