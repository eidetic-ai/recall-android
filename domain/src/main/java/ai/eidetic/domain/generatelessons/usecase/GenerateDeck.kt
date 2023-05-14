package ai.eidetic.domain.generatelessons.usecase

import ai.eidetic.domain.generatelessons.model.Deck
import ai.eidetic.domain.generatelessons.repository.GenerateLessonsRepository
import ai.eidetic.domain.infrastructure.QueryUseCase

interface GenerateDeck : QueryUseCase.WithRequest<List<String>, Deck> {

    class Default(
        private val repository: GenerateLessonsRepository,
    ) : GenerateDeck {
        override suspend fun invoke(request: List<String>) = repository.generateLessons(request)
    }
}
