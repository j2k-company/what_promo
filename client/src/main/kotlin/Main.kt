import canvas.CanvasState
import canvas.fps
import math.Point
import math.Vector
import objects.Mass
import objects.Object
import objects.RGB

val blackHole = Object(mass = Mass(Mass.MAX_VAlUE), radius = 50.0, color = RGB(20U, 20U, 20U))
var lastTime = 0
val cursor = AnimatedText(
    text = " ",
    interval = 3.fps,
    backgroundColor = RGB(50u, 50u, 50u)
)
val animatedText = AnimatedText(
    text = "someInnerTExt",
    interval = 6.fps,
    color = RGB(0u, 170u, 0u)
)

fun main() {
    CanvasState {
        if (blackHole !in drawables) {
            clear()
            blackHole.position = Point(width / 2.0, height / 2.0)
            addDrawable(blackHole)
            animatedText.startAnimation()
        }

        if (time != lastTime) {
            val position = randomEdgePoint()
            val direction = position vectorTo blackHole.position
            val speed = Vector(direction.x / direction.length, direction.y / direction.length)

            addDrawable(
                Object(
                    position = position,
                    radius = 3.0,
                    mass = Mass(10),
                    speed = speed
                )
            )

            lastTime = time
        }
    }
}
