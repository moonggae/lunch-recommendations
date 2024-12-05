package dev.egchoi

import dev.egchoi.service.MenuService
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

fun Application.configureRouting(
    menuService: MenuService
) {
    routing {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }

        post("/recommend") {
            try {
                val menu = menuService.getRandomMenu()

                val text = if (menu.spicyLevel >= 2) "${menu.name} 크으"
                else "${menu.name} 먹어"

                val response = KakaoResponse(
                    template = Template(
                        outputs = listOf(
                            Output(
                                simpleText = SimpleText(
                                    text = text
                                )
                            )
                        )
                    )
                )

                call.respond(response)
            } catch (e: Exception) {
                call.respond(
                    KakaoResponse(
                        template = Template(
                            outputs = listOf(
                                Output(
                                    simpleText = SimpleText(
                                        text = "고장났어"
                                    )
                                )
                            )
                        )
                    )
                )
                e.printStackTrace()
            }
        }
    }
}

@Serializable
data class KakaoRequest(
//    val intent: Intent?,
    val userRequest: UserRequest,
//    val bot: Bot?,
//    val action: Action?,
//    val contexts: List<ContextValue>? = emptyList()
)

@Serializable
data class ContextValue(
    val name: String,
    val lifeSpan: Int,
    val ttl: Int? = null,
    val params: Map<String, String> = emptyMap()
)

@Serializable
data class Intent(
    val id: String,
    val name: String,
    val extra: IntentExtra? = null
)

@Serializable
data class IntentExtra(
    val reason: Reason? = null,
    val knowledge: Knowledge? = null
)

@Serializable
data class Reason(
    val code: Int,
    val message: String
)

@Serializable
data class Knowledge(
    val responseType: String,
    val matchedKnowledges: List<MatchedKnowledge>
)

@Serializable
data class MatchedKnowledge(
    val categories: List<String>,
    val question: String,
    val answer: String,
    val imageUrl: String? = null,
    val landingUrl: String? = null
)

@Serializable
data class UserRequest(
    val timezone: String,
    val params: Map<String, String>,
    val block: Block,
    val utterance: String,
    val lang: String? = null,
    val user: User
)

@Serializable
data class Block(
    val id: String,
    val name: String
)

@Serializable
data class User(
    val id: String,
    val type: String,
    val properties: UserProperties = UserProperties()
)


@Serializable
data class UserProperties(
    val plusfriendUserKey: String? = null,
    val appUserId: String? = null,
    val isFriend: Boolean? = null
)

@Serializable
data class Bot(
    val id: String,
    val name: String
)

@Serializable
data class Action(
    val name: String,
//    val clientExtra: Map<String, String>? = emptyMap(),
//    val params: Map<String, String>? = emptyMap(),
    val id: String,
//    val detailParams: Map<String, String>? = emptyMap()
)

@Serializable
data class KakaoResponse @OptIn(ExperimentalSerializationApi::class) constructor(
    @EncodeDefault
    val version: String = "2.0",
    val template: Template
)

@Serializable
data class Template(
    val outputs: List<Output>
)

@Serializable
data class Output(
    val simpleText: SimpleText
)

@Serializable
data class SimpleText(
    val text: String
)