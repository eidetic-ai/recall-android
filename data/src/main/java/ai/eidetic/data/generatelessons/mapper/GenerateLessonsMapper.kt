package ai.eidetic.data.generatelessons.mapper

import ai.eidetic.data.generatelessons.api.Card
import ai.eidetic.data.generatelessons.api.DeckMetadata
import ai.eidetic.data.generatelessons.api.EideticApiObject
import ai.eidetic.data.generatelessons.api.Section
import ai.eidetic.domain.generatelessons.model.CardModel
import ai.eidetic.domain.generatelessons.model.Deck
import ai.eidetic.domain.generatelessons.model.MetadataDeckModel
import ai.eidetic.domain.generatelessons.model.SectionModel


class GenerateLessonsMapper {

    fun toDeck(data: EideticApiObject) =
        Deck(
            id = data.id.id.string,
            metadata = mapMetadata(data.metadata),
            sections = data.sections.map { mapSection(it) }
        )

    private fun mapMetadata(data: DeckMetadata) = MetadataDeckModel(
        name = data.name,
        due = data.due
    )

    private fun mapSection(section: Section) = SectionModel(
        topic = section.topic,
        text = section.text,
        cards = section.cards.map { card ->
            mapCard(card)
        }
    )

    private fun mapCard(card: Card) = CardModel(
        question = card.question,
        answer = card.answer,
        choices = card.choices,
        trueStatement = card.trueStatement,
        false_statements = card.falseStatements,
        ease = card.ease,
        phase = card.phase,
        interval = card.interval,
        due = card.due,
    )
}
