package ai.eidetic.service.service

import ai.eidetic.common.error.AppError
import android.net.Uri
import arrow.core.Either

interface TextDetectionService{
    suspend fun detectTextFrom(uri: Uri): Either<AppError.TextDetectionError, List<String>>
}