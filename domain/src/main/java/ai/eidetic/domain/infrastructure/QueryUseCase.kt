package ai.eidetic.domain.infrastructure

import arrow.core.Either

interface QueryUseCase<out Response> {
    suspend operator fun invoke(): Either<Exception, Response>

    interface WithRequest1<in Request, out Response> {
        suspend operator fun invoke(request: Request): List<List<String>>
    }

    interface WithRequest<in Request, out Response> {
        suspend operator fun invoke(request: Request): Either<Exception, Response>
    }
}
