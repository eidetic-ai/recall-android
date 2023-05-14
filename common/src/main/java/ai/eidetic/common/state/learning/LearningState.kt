package ai.eidetic.common.state.learning

import ai.eidetic.domain.generatelessons.model.Deck
import arrow.optics.optics

@optics
data class LearningState(
    val decks: List<Deck>,
    val selectedDeckID: String,
    val type: LevelType,
){
    companion object{
        val Initial = LearningState(
            emptyList(),
            "",
            LevelType.NOT_SELECTED
        )
    }
}

enum class LevelType{
    TRUE_FALSE,
    MULTIPLE_CHOICE,
    FREE_HAND,
    NOT_SELECTED
}