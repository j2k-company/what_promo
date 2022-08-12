import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080) {
        routing {
            singlePageApplication {
                useResources = true
                filesPath = "web"
                defaultPage = "index.html"
            }
        }
    }.start(wait = true)
}
