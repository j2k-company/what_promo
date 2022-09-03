import kotlinx.browser.document
import objects.RGB
import org.w3c.dom.HTMLDivElement

class Text(name: String, text: String, color: RGB) {
    private val textField: HTMLDivElement

    init {
        val div = document.createElement(name) as HTMLDivElement
        div.innerText = text
        div.style.apply {
            position = "absolute"
            this.color = color.hex
        }
        document.body!!.appendChild(div)

        textField = div
    }
}