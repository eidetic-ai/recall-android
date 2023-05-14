package ai.eidetic.common.state.deckCreationState

import ai.eidetic.common.state.appState.AppState
import ai.eidetic.common.state.appState.deckCreationState
import ai.eidetic.common.utils.loadDataFromJson
import ai.eidetic.common.utils.saveDataAsJson
import ai.eidetic.common.viewstore.ViewStore
import ai.eidetic.domain.deckcreation.usecase.GetParagraphsFromImages
import ai.eidetic.domain.generatelessons.model.Deck
import ai.eidetic.domain.generatelessons.model.MetadataDeckModel
import ai.eidetic.domain.generatelessons.usecase.GenerateDeck
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import arrow.core.continuations.either
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DeckCreationViewStore(
    private val getParagraphsFromImages: GetParagraphsFromImages,
    private val generateDeck: GenerateDeck,
) : ViewStore<DeckCreationState>(AppState.deckCreationState) {

    private val scope = CoroutineScope(Dispatchers.Default)

    fun updateImages(images: List<Uri>) {
        update { state ->
            state.copy(
                images = images
            )
        }
    }

    fun changeDeckTitle(title: String) {
        update { state ->
            DeckCreationState.deck.modify(state) {
                it.copy(
                    metadata = MetadataDeckModel(
                        name = title,
                        due = it.metadata.due
                    )
                )
            }
        }
    }

    fun changeDeckDueDate(date: String) {
        update { state ->
            DeckCreationState.deck.modify(state) {
                it.copy(
                    metadata = MetadataDeckModel(
                        name = it.metadata.name,
                        due = date
                    )
                )
            }
        }
    }

    fun addMultipleImages(images: List<Uri>) {
        update { state ->
            state.copy(
                images = state.images + images
            )
        }
    }

    fun addNewImage(image: Uri) {
        update { state ->
            state.copy(
                images = state.images + image
            )
        }
    }

    fun onNextClicked(uris: List<Uri>) = scope.launch {
        update { state ->
            state.copy(
                isDetectingText = true
            )
        }
        _getParagraphsFromImages(uris)
    }

    private suspend fun _getParagraphsFromImages(uris: List<Uri>) = either {
        val paragraphs = getParagraphsFromImages(uris)
            .flatten()
            .filter { it.length > 60 }
            .map { it.replace('\n', ' ') }
            .map { splitParagraphIntoChunks(it) }
            .flatten()
        try {
            val deck = generateDeck(paragraphs).bind()
            update { state ->
                state.copy(
                    isDetectingText = false,
                    images = emptyList(),
                    paragraphs = paragraphs,
                    deck = deck
                )
            }
        } catch (e: Exception) {
            update { state ->
                state.copy(
                    isDetectingText = false,
                    errorOccurred = true
                )
            }
            println("[error] Error occurred in generating lessons: $e")
        }
    }

    fun splitParagraphIntoChunks(paragraph: String): List<String> {
        val words = paragraph.split(" ")
        val chunks = mutableListOf<String>()
        var chunk = ""
        for (i in 0 until words.size) {
            chunk += words[i] + " "
            if ((i + 1) % 100 == 0) {
                chunks.add(chunk.trim())
                chunk = ""
            }
        }
        if (chunk.isNotBlank()) {
            chunks.add(chunk.trim())
        }
        return chunks
    }

    fun saveData(file: File, data: Deck){
        saveDataAsJson(file, data)
    }

    fun loadData(file: File, id: String): Deck?{
        return loadDataFromJson(file, id)
    }
}