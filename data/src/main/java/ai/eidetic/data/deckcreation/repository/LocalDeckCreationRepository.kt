package ai.eidetic.data.deckcreation.repository

import ai.eidetic.service.service.TextDetectionService
import ai.eidetic.domain.deckcreation.repository.DeckCreationRepository
import android.net.Uri
import arrow.core.Either

class LocalDeckCreationRepository(
    private val textDetectionService: TextDetectionService,
):DeckCreationRepository {
    override suspend fun getParagraphsFromImages(uris: List<Uri>): List<List<String>> {
        return uris
            .map { textDetectionService.detectTextFrom(it).mapLeft { println("[Error] Detecting text from image. ") } }
            .filterIsInstance<Either.Right<List<String>>>()
            .map { it.value }
    }
}