package objects

import canvas.CanvasState
import canvas.Drawable
import canvas.strokeFigure
import math.*
import org.w3c.dom.CanvasRenderingContext2D
import kotlin.math.PI
import kotlin.math.pow
import kotlin.random.Random


const val GRAVITATIONAL_CONST: Double = 6.6743e-5

open class Object(
    mass: Mass = Mass(100000),
    val radius: Double = 10.0,
    var speed: Vector = Vector(.0, .0),
    var position: Point = Point.random(),
    val color: RGB = RGB.random()
) : Drawable {
    private var acceleration = Vector()
    private val mass: Long = mass.value

    private fun move() {
        position.x += speed.x
        position.y += speed.y
    }

    override fun draw(context: CanvasRenderingContext2D) = strokeFigure(context, color, color) {
        arc(position.x, position.y, radius, 0.0, 2 * PI, false)
    }

    override fun update(canvasState: CanvasState) {
        acceleration = Vector()
        var force = Vector()

        canvasState.drawables.forEach { obj ->
            if (obj is Object && position distanceTo obj.position != .0) {
                force += this attractionBy obj
            }
        }

        acceleration = Vector(force.x / mass, force.y / mass)
        speed += acceleration
        move()
    }

    infix fun attractionBy(obj: Object): Vector {
        val squaredR = (position distanceTo obj.position).pow(2.0)

        val xCoef: Byte = if (position.x < obj.position.x) 1 else -1
        val yCoef: Byte = if (position.y < obj.position.y) 1 else -1

        val massProduct = mass * obj.mass

        return Vector(
            GRAVITATIONAL_CONST * massProduct / squaredR * xCoef,
            GRAVITATIONAL_CONST * massProduct / squaredR * yCoef
        )
    }
}

value class Mass(val value: Long) {
    init {
        require(value <= 3037000499) // ~sqrt(Long.MAX_VALUE)
    }

    companion object {
        val MAX_VAlUE: Long = 3037000499
    }
}

data class RGB(val r: UByte, val g: UByte, val b: UByte) {
    val hex = "#${if (r < 10U) "0" else "" + r.toString(16)}" +
            (if (r < 10U) "0" else "" + g.toString(16)) +
            if (r < 10U) "0" else "" + b.toString(16)

    companion object {
        fun random() = RGB(
            Random.nextUByte(),
            Random.nextUByte(),
            Random.nextUByte(),
        )
    }
}

fun Random.nextUByte() = nextInt(255).toUByte()

