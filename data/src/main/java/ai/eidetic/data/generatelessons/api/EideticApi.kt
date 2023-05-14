package ai.eidetic.data.generatelessons.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

interface EideticApi {
    @POST("/worker/generate-cards")
    suspend fun getQuestions(
        @Body eideticApiRequest: List<String>
    ): EideticApiObject
}

@Serializable
data class EideticApiObject(
    @SerialName("id")
    val id: DeckID,
    @SerialName("metadata")
    val metadata: DeckMetadata,
    @SerialName("sections")
    val sections: List<Section>
)

@Serializable
data class DeckID(
    @SerialName("tb")
    val tb: String,
    @SerialName("id")
    val id: ID,
)

@Serializable
data class DeckMetadata(
    @SerialName("name")
    val name: String,
    @SerialName("due")
    val due: String,
    @SerialName("scheduler")
    val scheduler: Scheduler,
)

@Serializable
data class Scheduler(
    @SerialName("name")
    val name: String,
    @SerialName("version")
    val version: Int,
)

@Serializable
data class ID(
    @SerialName("String")
    val string: String,
)

@Serializable
data class Section(
    @SerialName("topic")
    val topic: String,
    @SerialName("text")
    val text: String,
    @SerialName("cards")
    val cards: List<Card>,
)

@Serializable
data class Card(
    @SerialName("question")
    val question: String,
    @SerialName("answer")
    val answer: String,
    @SerialName("choices")
    val choices: List<String>,
    @SerialName("true_statement")
    val trueStatement: String,
    @SerialName("false_statements")
    val falseStatements: List<String>,
    @SerialName("ease")
    val ease: Float,
    @SerialName("phase")
    val phase: String,
    @SerialName("interval")
    val interval: Int,
    @SerialName("due")
    val due: String,
)
