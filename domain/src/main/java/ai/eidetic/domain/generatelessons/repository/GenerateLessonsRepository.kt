package ai.eidetic.domain.generatelessons.repository

import ai.eidetic.domain.generatelessons.model.Deck
import arrow.core.Either

interface GenerateLessonsRepository {
    suspend fun generateLessons(paragraphs: List<String>): Either<Exception, Deck>
}
