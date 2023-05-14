package ai.eidetic.common.state.appState

import ai.eidetic.common.state.appAccessState.AppAccessState
import ai.eidetic.common.state.deckCreationState.DeckCreationState
import arrow.optics.optics

@optics
data class AppState(
    val appAccessState: AppAccessState,
    val deckCreationState: DeckCreationState,
){
    companion object{
        val Initial = AppState(
            appAccessState = AppAccessState.Initial,
            deckCreationState = DeckCreationState.Initial,
        )
    }
}