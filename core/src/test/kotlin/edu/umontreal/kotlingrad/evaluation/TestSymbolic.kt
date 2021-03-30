package edu.umontreal.kotlingrad.evaluation

import edu.umontreal.kotlingrad.*
import edu.umontreal.kotlingrad.api.*
import io.kotest.core.spec.style.StringSpec
import io.kotest.property.*
import javax.script.*
import javax.script.ScriptContext.ENGINE_SCOPE
import kotlin.math.pow
import kotlin.random.Random

@Suppress("NonAsciiCharacters")
class TestSymbolic : StringSpec({
  val engine = ScriptEngineManager().getEngineByExtension("kts")

  val x by SVar(DReal)
  val y by SVar(DReal)
  val z by SVar(DReal)

  fun ktf(f: SFun<DReal>, vararg kgBnds: Pair<SVar<DReal>, Number>) =
    engine.run {
      try {
        val bnds = kgBnds.associate { it.first.name to it.second.toDouble() }
        setBindings(SimpleBindings(bnds), ENGINE_SCOPE)
        val expr = "import kotlin.math.*; $f"
        eval(expr)
      } catch (e: Exception) {
        System.err.println("Failed to evaluate expression: $f")
        throw e
      }
    }

  "test symbolic evaluation" {
    checkAll(10, TestExpressionGenerator(DReal)) { f ->
      checkAll(3, DoubleGenerator, DoubleGenerator, DoubleGenerator) { ẋ, ẏ, ż ->
        f(x to ẋ, y to ẏ, z to ż) shouldBeAbout ktf(f, x to ẋ, y to ẏ, z to ż)
      }
    }
  }
})

class TestExpressionGenerator<X : RealNumber<X, *>>(proto: X) : Arb<SFun<X>>() {
  val expGen = ExpressionGenerator<X>()

  override fun values(rs: RandomSource): Sequence<Sample<SFun<X>>> =
    generateSequence { Sample(expGen.randomBiTree()) }

  override fun edgecases(): List<SFun<X>> = emptyList()
}