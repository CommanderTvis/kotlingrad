package edu.umontreal.kotlingrad.samples

import edu.umontreal.kotlingrad.experimental.*

@Suppress("DuplicatedCode")
fun main() = with(DoublePrecision) {
  val f = x pow 2
  println(f(x to 3.0))
  println("f(x) = $f")
  val df_dx = f.d(x)
  println("f'(x) = $df_dx")

  val g = x pow x
  println("g(x) = $g")
  val dg_dx = g.d(x)
  println("g'(x) = $dg_dx")

  val h = x + y
  println("h(x) = $h")
  val dh_dx = h.d(x)
  println("h'(x) = $dh_dx")

  val vf1 = Vec(y + x, y * 2)
  println(vf1)
  val bh = x * vf1 + Vec(1.0, 3.0)
  println(bh(y to 2.0, x to 4.0))
  val vf2 = Vec(x, y)
  val q = vf1 + vf2 + Vec(0.0, 0.0)
  val z = q(x to 1.0).magnitude()(y to 2.0)
  println(z)

  val vf3 = vf2 ʘ Vec(x, x)
  val mf1 = vf3.d(x, y)
//    println(vf3.d(x)(y to 2.0))
}