package math

import kotlin.math.sqrt


class Vector(val x: Double = .0, val y: Double = .0) {
    val sqr: Double = x * x + y * y
    val length = sqrt(sqr)
    val squaredVector: Vector
        get() = Vector(x * x, y * y)

    operator fun plus(v: Vector) = Vector(x + v.x, y + v.y)
    operator fun unaryMinus() = Vector(-x, -y)
    operator fun minus(v: Vector) = Vector(x - v.x, y - v.y)

    override fun toString(): String = "x = $x, y = $y"
}
