package ai.eidetic.domain.deckcreation.repository

import android.net.Uri

interface DeckCreationRepository{
    suspend fun getParagraphsFromImages(uris: List<Uri>): List<List<String>>
}