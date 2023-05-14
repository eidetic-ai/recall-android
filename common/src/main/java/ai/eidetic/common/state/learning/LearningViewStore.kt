package ai.eidetic.common.state.learning

import ai.eidetic.common.state.appState.AppState
import ai.eidetic.common.state.appState.deckCreationState
import ai.eidetic.common.state.appState.learningState
import ai.eidetic.common.state.deckCreationState.DeckCreationState
import ai.eidetic.common.state.deckCreationState.deck
import ai.eidetic.common.utils.loadDataFromJson
import ai.eidetic.common.utils.loadDataFromJsonWithId
import ai.eidetic.common.utils.saveDataAsJson
import ai.eidetic.common.viewstore.ViewStore
import ai.eidetic.domain.deckcreation.usecase.GetParagraphsFromImages
import ai.eidetic.domain.generatelessons.model.CardModel
import ai.eidetic.domain.generatelessons.model.Deck
import ai.eidetic.domain.generatelessons.model.MetadataDeckModel
import ai.eidetic.domain.generatelessons.usecase.GenerateDeck
import android.net.Uri
import arrow.core.continuations.either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception
import java.lang.Float.max
import java.lang.Float.min

class LearningViewStore() : ViewStore<LearningState>(AppState.learningState) {

    private val scope = CoroutineScope(Dispatchers.Default)

    fun updateDecks(file: File) {
        val decks = loadDataFromJson(file)

        update { state ->
            state.copy(
                decks = decks.map { deck -> deck.copy(mastery = max(min(calculateMastery(deck), 1.0f), 0.0f)) }
            )
        }
    }

    fun selectDeck(deck: Deck){
        update { state ->
            state.copy(
                selectedDeckID = deck.id
            )
        }
    }

    fun checkIfTrue(trueStatements: List<String>, check: Boolean, statement: String): Boolean {
        return if (check) {
            trueStatements.contains(statement)
        } else {
            trueStatements.contains(statement).not()
        }
    }

    fun checkChoices(card: CardModel, choice: String): Boolean {
        return card.answer == choice
    }

    fun setLevelType(type: LevelType){
        update { state ->
            state.copy(
                type = type
            )
        }
    }
//    fun updateMasteryForDeck(deck: Deck) {
//        update { state ->
//            state.copy(
//                decks = state.decks.map {
//                    if (it.id != deck.id) it else it.copy(
//                        id = it.id,
//                        metadata = it.metadata,
//                        sections = it.sections,
//                        mastery = calculateMastery(deck)
//                    )
//                }
//            )
//        }
//    }

    private fun calculateMastery(deck: Deck) =
        (max(deck.sections.map { section -> section.cards }.flatten().map { card -> card.ease}.average().toFloat(), 2.5f) - 2.5f) / 0.5f

    fun saveData(file: File, data: Deck) {
        saveDataAsJson(file, data)
    }

    fun loadData(file: File, id: String): Deck? {
        return loadDataFromJsonWithId(file, id)
    }
}