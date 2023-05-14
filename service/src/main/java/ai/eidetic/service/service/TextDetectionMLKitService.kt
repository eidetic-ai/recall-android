package ai.eidetic.service.service

import ai.eidetic.common.error.AppError
import android.content.Context
import android.net.Uri
import arrow.core.Either
import arrow.core.computations.ResultEffect.bind
import arrow.core.continuations.either
import arrow.core.left
import arrow.core.right
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.CompletableDeferred

class TextDetectionMLKitService(
    private val context: Context,
) : TextDetectionService {
    private val recognizer by lazy { TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS) }

    override suspend fun detectTextFrom(uri: Uri): Either<AppError.TextDetectionError, List<String>> = either {
        val image = getImage(uri).bind()

        val completable = CompletableDeferred<Either<Exception, List<String>>>()

        recognizer.process(image)
            .addOnSuccessListener {
                completable.complete(it.textBlocks.map { block -> block.text }.right())
            }
            .addOnFailureListener {
                completable.complete(it.left())
            }

        completable.await().bind()
    }

    private fun getImage(uri: Uri) = try {
        InputImage.fromFilePath(context, uri).right()
    } catch (e: Exception) {
        AppError.TextDetectionError.left()
    }
}