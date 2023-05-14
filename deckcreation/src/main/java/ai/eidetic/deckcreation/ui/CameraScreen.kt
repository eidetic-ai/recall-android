package ai.eidetic.deckcreation.ui

import ai.eidetic.common.state.deckCreationState.DeckCreationViewStore
import ai.eidetic.common.ui.theme.ColorPallet
import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.OnImageSavedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@Composable
fun CameraScreen(
    navController: NavController,
    file: File,
    viewStore: DeckCreationViewStore = getViewModel()
) {
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

        Box(
            modifier = Modifier
                .padding(bottom = 32.dp)
                .align(Alignment.BottomCenter)
                .size(60.dp)
                .background(color = ColorPallet.neutral0, CircleShape)
                .clip(CircleShape)
                .clickable {
                    takeImage(
                        filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                        imageCapture = imageCapture,
                        outputDirectory = file,
                        onImageCapture = {
                            viewStore.addNewImage(it)
                            Handler(Looper.getMainLooper()).post {
                                navController.popBackStack()
                            }
                        },
                        onError = {
                            Handler(Looper.getMainLooper()).post {
                                Toast
                                    .makeText(context, "There was an error. Try again.", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    )
                }
        ) {
            Box(
                Modifier.align(Alignment.Center)
                    .size(52.dp)
                    .background(color = ColorPallet.neutral0, CircleShape)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        shape = CircleShape,
                        brush = Brush.linearGradient(listOf(ColorPallet.neutral300, ColorPallet.neutral300))
                    )
            )
        }
    }

}

private fun takeImage(
    filenameFormat: String,
    imageCapture: ImageCapture,
    outputDirectory: File,
    onImageCapture: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit,
) {
    val executor = Executors.newSingleThreadExecutor()
    val photoFile = File(
        outputDirectory,
        SimpleDateFormat(filenameFormat, Locale.ENGLISH).format(System.currentTimeMillis()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(outputOptions, executor, object : OnImageSavedCallback {
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            val savedUri = Uri.fromFile(photoFile)
            onImageCapture(savedUri)
            executor.shutdownNow()
        }

        override fun onError(exception: ImageCaptureException) {
            onError(exception)
        }
    })
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}

