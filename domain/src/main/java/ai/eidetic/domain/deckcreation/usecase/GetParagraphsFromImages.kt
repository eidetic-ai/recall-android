package ai.eidetic.domain.deckcreation.usecase

import ai.eidetic.domain.deckcreation.repository.DeckCreationRepository
import ai.eidetic.domain.infrastructure.QueryUseCase
import android.net.Uri

interface GetParagraphsFromImages: QueryUseCase.WithRequest1<List<Uri>, List<List<String>>> {

    class Default(
        private val repository: DeckCreationRepository,
    ): GetParagraphsFromImages{
        override suspend fun invoke(request: List<Uri>) = repository.getParagraphsFromImages(request)
    }
}