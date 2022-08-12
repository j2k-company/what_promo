package math

import kotlinx.browser.window
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

data class Point(var x: Double, var y: Double) {
    companion object {
        fun random() = Point(
            Random.nextDouble(window.innerWidth.toDouble()),
            Random.nextDouble(window.innerHeight.toDouble()),
        )
    }

    infix fun distanceTo(point: Point): Double =
        sqrt((x - point.x).pow(2.0) + (y - point.y).pow(2.0))

    infix fun vectorTo(point: Point): Vector = Vector(
        (x - point.x), (y - point.y)
    )

    override fun toString(): String = "x = $x, y = $y"
}
