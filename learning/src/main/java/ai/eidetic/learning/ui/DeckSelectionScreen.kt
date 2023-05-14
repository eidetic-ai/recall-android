package ai.eidetic.learning.ui

import ai.eidetic.common.state.learning.LearningViewStore
import ai.eidetic.common.ui.components.VerticalSpacer
import ai.eidetic.common.ui.theme.ColorPallet
import ai.eidetic.domain.generatelessons.model.Deck
import ai.eidetic.common.R
import ai.eidetic.common.ui.components.Button
import ai.eidetic.common.ui.components.HorizontalSpacer
import ai.eidetic.common.ui.theme.body4
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.getViewModel
import java.io.File

@Composable
fun DeckSelectionScreen(
    navController: NavHostController,
    onDeckSelected: () -> Unit,
    onAddMore: () -> Unit,
    file: File,
    viewStore: LearningViewStore = getViewModel(),
) {
    LaunchedEffect(Unit) {
        viewStore.updateDecks(file)
    }

    val state = viewStore()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TopAppBar(
                backgroundColor = ColorPallet.neutral300,
                contentColor = ColorPallet.neutral0,
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Deck Selection",
                        style = MaterialTheme.typography.subtitle1.copy(color = ColorPallet.neutral0),
                        textAlign = TextAlign.Center
                    )
                }
            }
            LazyColumn {
                items(state.decks) { deck ->
                    VerticalSpacer(value = 24.dp)

                    DeckView(deck, { viewStore.selectDeck(it) }, onDeckSelected)
                }

                item {
                    VerticalSpacer(value = 24.dp)
                }
            }

        }
        FloatingActionButton(
            backgroundColor = ColorPallet.neutral300,
            modifier = Modifier
                .padding(end = 24.dp, bottom = 24.dp)
                .size(48.dp)
                .align(Alignment.BottomEnd),
            onClick = onAddMore
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "",
                    tint = ColorPallet.neutral30
                )
            }
        }
    }
}

@Composable
fun DeckView(deck: Deck, selectDeck: (Deck) -> Unit, onDeckSelected: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .background(color = ColorPallet.neutral40, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = deck.metadata.name,
                style = MaterialTheme.typography.h5.copy(ColorPallet.neutral900, fontWeight = FontWeight.Bold),
            )

            Row(
                modifier = Modifier
                    .background(color = ColorPallet.neutral300, shape = RoundedCornerShape(24.dp))
                    .padding(vertical = 4.dp, horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = "Calendar",
                    tint = ColorPallet.neutral40
                )

                HorizontalSpacer(value = 6.dp)

                Text(
                    text = deck.metadata.due,
                    style = MaterialTheme.typography.body4.copy(ColorPallet.neutral40),
                )
            }
        }

        VerticalSpacer(value = 12.dp)


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mastery:",
                style = MaterialTheme.typography.body1.copy(ColorPallet.neutral900, fontWeight = FontWeight.Bold),
            )

            HorizontalSpacer(value = 8.dp)

            ProgressBar(progress = deck.mastery)
        }

        VerticalSpacer(value = 12.dp)

        Button(
            modifier = Modifier.fillMaxWidth(),
            padding = PaddingValues(0.dp),
            text = "Start learning",
            backgroundColorDefault = ColorPallet.neutral300,
            onClick = {
                selectDeck(deck)
                onDeckSelected()
            }
        )
    }
}

@Composable
fun ProgressBar(progress: Float) {
    LinearProgressIndicator(
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(12.dp)),
        color = getColor(progress)
    )
}

fun getColor(progress: Float) = when {
    progress < 0.5 -> ColorPallet.systemError500
    progress > 0.5 && progress < 0.7 -> ColorPallet.systemWarning500
    else -> ColorPallet.systemSuccess500
}


