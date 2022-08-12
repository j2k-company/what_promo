package canvas

import objects.RGB
import kotlinx.browser.document
import kotlinx.browser.window
import math.Point
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.random.Random

class CanvasState(
    var interval: Int = 60.fps,
    val canvas: HTMLCanvasElement = initalizeCanvas(),
    val backgroundColor: RGB = RGB(0U, 0U, 0U),
    val entry: (CanvasState.() -> Unit)? = null
) {
    private val _drawables = mutableListOf<Drawable>()
    val drawables: List<Drawable>
        get() = _drawables

    val width: Int
        get() = canvas.width
    val height: Int
        get() = canvas.height
    val context = canvas.getContext("2d") as CanvasRenderingContext2D

    private var iterations = 0
    val time: Int
        get() = iterations * interval / 1000

    init {
        window.setInterval({
//            clear()
            entry?.invoke(this@CanvasState)
            _drawables.forEach {
                it.update(this@CanvasState)
                it.draw(context)
            }

            iterations++
        }, interval)
    }

    fun clear() = strokeFigure(context, strokeColor = backgroundColor, fillColor = backgroundColor) {
        rect(0.0, 0.0, width.toDouble(), height.toDouble())
    }

    fun addDrawable(drawable: Drawable): Drawable {
        _drawables.add(drawable)
        return drawable
    }

    fun removeDrawable(drawable: Drawable): Drawable {
        _drawables.remove(drawable)
        return drawable
    }

    fun randomEdgePoint(): Point {
        return if (Random.nextBoolean())
            Point(Random.nextDouble(width.toDouble()), listOf(0.0, height.toDouble()).random())
        else
            Point(listOf(0.0, width.toDouble()).random(), Random.nextDouble(height.toDouble()))
    }

    companion object {
        fun initalizeCanvas(): HTMLCanvasElement {
            val canvas = document.createElement("canvas") as HTMLCanvasElement
            val context = canvas.getContext("2d") as CanvasRenderingContext2D
            context.canvas.width = window.innerWidth
            context.canvas.height = window.innerHeight
            document.body!!.appendChild(canvas)
            return canvas
        }
    }
}

fun canvasState(
    interval: Int = 30.fps,
    canvas: HTMLCanvasElement = CanvasState.initalizeCanvas(),
    backgroundColor: RGB = RGB(148U, 148U, 148U),
    entry: (CanvasState.() -> Unit)? = null
) {
    CanvasState(interval, canvas, backgroundColor, entry)
}

interface Drawable {
    fun draw(context: CanvasRenderingContext2D)
    fun update(canvasState: CanvasState)
}

val Number.fps: Int
    get() = 1000 / this.toInt()
