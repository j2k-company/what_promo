import kotlinx.browser.document
import kotlinx.browser.window
import objects.RGB
import org.w3c.dom.HTMLDivElement

class AnimatedText(
    private val text: String,
    color: RGB,
    private val interval: Int,
    private val animation: AnimatedText.() -> Unit
) {

    private val textField: HTMLDivElement
    private var handle: Int? = null
    var currentCharIndex: Int = 0
        private set

    val currentChar: Char
        get() = text[currentCharIndex]

    init {
        val div = document.createElement("div") as HTMLDivElement
        div.style.apply {
            position = "absolute"
            console.log(color.hex)
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