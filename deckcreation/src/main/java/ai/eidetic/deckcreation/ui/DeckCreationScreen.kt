package ai.eidetic.deckcreation.ui

import ai.eidetic.common.state.deckCreationState.DeckCreationViewStore
import ai.eidetic.common.ui.components.Button
import ai.eidetic.common.ui.components.HorizontalSpacer
import ai.eidetic.common.ui.components.VerticalSpacer
import ai.eidetic.common.ui.theme.ColorPallet
import ai.eidetic.common.R
import ai.eidetic.common.ui.components.dashedBorder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DeckCreationScreen(
    navController: NavHostController,
    onTakePhoto: () -> Unit,
    goToFinalizeDeck: () -> Unit,
    viewStore: DeckCreationViewStore = getViewModel(),
) {
    val state = viewStore()


    val cameraPermissionState = rememberPermissionState(
        permission = android.Manifest.permission.CAMERA
    )

    val selectImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris -> viewStore.addMultipleImages(uris) }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopAppBar(
            backgroundColor = ColorPallet.neutral300,
            contentColor = ColorPallet.neutral0,
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Deck Creation",
                    style = MaterialTheme.typography.subtitle1.copy(color = ColorPallet.neutral0),
                    textAlign = TextAlign.Center
                )
            }

        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
                .padding(24.dp)
                .dashedBorder(
                    strokeWidth = 2.dp,
                    color = ColorPallet.neutral300,
                    cornerRadiusDp = 16.dp
                ),
            verticalArrangement = Arrangement.Center
        ) {
            item {
                if (state.images.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            modifier = Modifier.size(80.dp),
                            painter = painterResource(id = R.drawable.ic_image),
                            contentDescription = "image icon",
                            tint = ColorPallet.neutral300
                        )

                        VerticalSpacer(value = 16.dp)

                        Text(
                            modifier = Modifier.padding(horizontal = 24.dp),
                            text = "Select or take images and start learning.",
                            style = MaterialTheme.typography.h5.copy(color = ColorPallet.neutral300),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            itemsIndexed(state.images) { index, images ->
                Box(
                    modifier = Modifier
                        .padding(24.dp)
                        .height(400.dp)
                ) {
                    AsyncImage(
                        model = images,
                        contentDescription = null,
                        modifier = Modifier
                            .height(400.dp)
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .offset(x = 8.dp, y = (-8).dp)
                            .align(Alignment.TopEnd)
                            .size(20.dp)
                            .background(
                                color = ColorPallet.systemError500,
                                shape = CircleShape
                            )
                            .clip(
                                CircleShape
                            )
                            .clickable {
                                viewStore.updateImages(state.images.filterIndexed { uriIndex, _ -> uriIndex != index })
                            },
                    ) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(10.dp),
                            painter = painterResource(id = R.drawable.ic_cross),
                            tint = ColorPallet.neutral0,
                            contentDescription = ""
                        )
                    }

                    HorizontalSpacer(value = 25.dp)
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.weight(1.0f),
                padding = PaddingValues(start = 24.dp, end = 12.dp),
                text = "Take Image",
                onClick = {
                    cameraPermissionState.launchPermissionRequest()
                    if(cameraPermissionState.hasPermission){
                        onTakePhoto()
                    }
                }
            )

            Button(
                modifier = Modifier.weight(1.0f),
                padding = PaddingValues(start = 12.dp, end = 24.dp),
                text = "Choose Image",
                onClick = {
                    selectImagePickerLauncher.launch(
                        PickVisualMediaRequest(
                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            )
        }

        VerticalSpacer(value = 24.dp)

        Button(
            modifier = Modifier.fillMaxWidth(),
            text = if(state.errorOccurred) "Try again" else "Next",
            isEnabled = state.images.isNotEmpty(),
            isLoading = state.isDetectingText,
            onClick = {
                viewStore.onNextClicked(state.images)
            }
        )

        VerticalSpacer(value = 24.dp)
    }

    LaunchedEffect(state.paragraphs.isNotEmpty()){
        if(state.paragraphs.isNotEmpty()){
            goToFinalizeDeck()
        }
    }
}

