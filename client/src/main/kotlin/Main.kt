import canvas.CanvasState
import math.Point
import math.Vector
import objects.Mass
import objects.Object
import objects.RGB

val blackHole = Object(mass = Mass(Mass.MAX_VAlUE), radius = 50.0, color = RGB(20U, 20U, 20U))
var lastTime = 0

fun main() {
    CanvasState {
        if (blackHole !in drawables) {
            clear()
            blackHole.position = Point(width / 2.0, height / 2.0)
            addDrawable(blackHole)
        }

        if (time != lastTime) {
            val position = randomEdgePoint()
            val direction = position vectorTo blackHole.position
            val speed = Vector(direction.x / direction.length, direction.y / direction.length)

            addDrawable(Object(position = position, radius = 3.0, mass = Mass(10), speed = speed))

            console.log("is worked")
            lastTime = time
        }
    }
}