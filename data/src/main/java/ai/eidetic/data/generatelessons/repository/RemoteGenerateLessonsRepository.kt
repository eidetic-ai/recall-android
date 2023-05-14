package ai.eidetic.data.generatelessons.repository

import ai.eidetic.data.generatelessons.api.EideticApi
import ai.eidetic.data.generatelessons.mapper.GenerateLessonsMapper
import ai.eidetic.domain.generatelessons.model.Deck
import ai.eidetic.domain.generatelessons.repository.GenerateLessonsRepository
import arrow.core.Either
import arrow.core.right

class RemoteGenerateLessonsRepository(
    private val eideticApi: EideticApi,
    private val mapper: GenerateLessonsMapper,
) : GenerateLessonsRepository {
    override suspend fun generateLessons(paragraphs: List<String>): Either<Exception, Deck> {
        val response = eideticApi.getQuestions(paragraphs)
        return mapper.toDeck(response).right()
    }
}
