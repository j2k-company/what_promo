import kotlinx.browser.document
import kotlinx.browser.window
import objects.RGB
import org.w3c.dom.HTMLDivElement

class AnimatedText(
    val text: String,
    color: RGB,
    private val interval: Int,
    val animation: AnimatedText.() -> Unit
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
            this.color = color.hex
        }
        document.body!!.appendChild(div)

        textField = div
    }

    fun nextChar(): Char? {
        return if(currentCharIndex != text.lastIndex) {
            currentCharIndex++
            currentChar
        } else {
            currentCharIndex = 0
            null
        }
    }

    fun startAnimation() {
        textField.innerText = ""
        handle = window.setInterval({
            animation.invoke(this)
        }, interval)
    }

    fun breakAnimation() {
        handle?.let { window.clearInterval(it) }
    }
}