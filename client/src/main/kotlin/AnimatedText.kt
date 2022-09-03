import canvas.fps
import kotlinx.browser.document
import kotlinx.browser.window
import math.Point
import objects.RGB
import org.w3c.dom.HTMLDivElement

class AnimatedText(
    private val text: String,
    private val interval: Int = 5.fps,
    color: RGB = RGB(0u, 0u, 0u),
    backgroundColor: RGB? = null,
    var position: Point = Point(0.0, 0.0),
    private val animation: AnimatedText.() -> Unit = { nextChar() }
) {

    private val textField: HTMLDivElement
    private var handle: Int? = null
    var currentCharIndex: Int = 0
        private set

    val currentChar: Char
        get() = text[currentCharIndex]

    val isAnimatedNow: Boolean
        get() = handle != null

    init {
        val div = document.createElement("div") as HTMLDivElement
        div.style.apply {
            top = this@AnimatedText.position.y.toString() + "dp"
            left = this@AnimatedText.position.x.toString() + "dp"
            this.position = "absolute"

            zIndex = "100"

            backgroundColor?.let { this.backgroundColor = it.hex }
            this.color = color.hex
        }
        document.body!!.appendChild(div)

        textField = div
    }

    fun nextChar(): Char? {
        return if(currentCharIndex != text.lastIndex) {
            textField.innerText += currentChar
            currentCharIndex++
            currentChar
        } else {
            textField.innerText += currentChar
            currentCharIndex = 0
            stopAnimation()
            null
        }
    }

    fun startAnimation() {
        textField.innerText = ""
        handle = window.setInterval({
            animation.invoke(this)
        }, interval)
    }

    fun stopAnimation() {
        handle?.let { window.clearInterval(it) }
    }
}