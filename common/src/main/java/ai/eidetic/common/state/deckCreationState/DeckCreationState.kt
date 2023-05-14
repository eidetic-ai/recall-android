package ai.eidetic.common.state.deckCreationState

import ai.eidetic.domain.generatelessons.model.Deck
import ai.eidetic.domain.generatelessons.model.MetadataDeckModel
import android.net.Uri
import arrow.optics.optics

@optics
data class DeckCreationState(
    val images: List<Uri>,
    val paragraphs: List<String>,
    val isDetectingText: Boolean,
    val deck: Deck,
    val errorOccurred: Boolean,
){
    companion object{
        val Initial = DeckCreationState(
            images = emptyList(),
            paragraphs = emptyList(),
            isDetectingText = false,
            deck = Deck(
                id = "",
                metadata = MetadataDeckModel(
                    name = "",
                    due = ""
                ),
                sections = emptyList(),
                mastery = 0.0f
            ),
            errorOccurred = false,
        )
    }
}