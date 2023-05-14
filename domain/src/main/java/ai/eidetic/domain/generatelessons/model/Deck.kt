package ai.eidetic.domain.generatelessons.model

data class Deck(
    val id: String,
    val metadata: MetadataDeckModel,
    val sections: List<SectionModel>
)

data class MetadataDeckModel(
    val name: String,
    val due: String,
)

data class SectionModel(
    val topic: String,
    val text: String,
    val cards: List<CardModel>
)

data class CardModel(
    val question: String,
    val answer: String,
    val choices: List<String>,
    val trueStatement: String,
    val false_statements: List<String>,
    val ease: Float,
    val phase:String,
    val interval: Int,
    val due: String,
)
