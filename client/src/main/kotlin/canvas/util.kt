package canvas

import objects.RGB
import org.w3c.dom.CanvasRenderingContext2D

/**
 * drawing the figure and filling this if the fillColor != null
 */
inline fun strokeFigure(
    context: CanvasRenderingContext2D,
    strokeColor: RGB,
    fillColor: RGB? = null,
    figureDraw: CanvasRenderingContext2D.() -> Unit,
) {
    context.apply {
        beginPath()

        strokeStyle = strokeColor.hex
        figureDraw(this)

        if (fillColor != null) {
            fillStyle = fillColor.hex
            fill()
        }

        stroke()
    }
}
