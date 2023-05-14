package ai.eidetic.learning.ui

import ai.eidetic.common.state.learning.LearningViewStore
import ai.eidetic.common.ui.components.VerticalSpacer
import ai.eidetic.common.ui.theme.ColorPallet
import ai.eidetic.common.R
import ai.eidetic.common.state.learning.LevelType
import ai.eidetic.common.ui.components.HorizontalSpacer
import ai.eidetic.common.ui.theme.body4
import ai.eidetic.common.ui.theme.subtitle3
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.getViewModel

@Composable
fun LearningScreen(
    navController: NavHostController,
    onStartLevelClicked: () -> Unit,
    viewStore: LearningViewStore = getViewModel(),
) {
    val state = viewStore()
    val selectedDeck = state.decks.firstOrNull { it.id == state.selectedDeckID }
    if (selectedDeck != null) {
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
                            text = "Learning ${selectedDeck.metadata.name}",
                            style = MaterialTheme.typography.subtitle1.copy(color = ColorPallet.neutral0),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                VerticalSpacer(value = 24.dp)

                TrueFalseQuestionView {
                    onStartLevelClicked()
                    viewStore.setLevelType(LevelType.TRUE_FALSE)
                }

                VerticalSpacer(value = 24.dp)

                MultipleChoiceQuestionView {
                    onStartLevelClicked()
                    viewStore.setLevelType(LevelType.MULTIPLE_CHOICE)
                }

                VerticalSpacer(value = 24.dp)

                FreeHandQuestionView {
                    onStartLevelClicked()
                    viewStore.setLevelType(LevelType.FREE_HAND)
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Something went wrong..."
            )
        }
    }
}

@Composable
private fun TrueFalseQuestionView(
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .background(color = ColorPallet.neutral40, shape = RoundedCornerShape(16.dp))
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "True False Level",
                style = MaterialTheme.typography.subtitle1.copy(ColorPallet.neutral900).copy(fontWeight = FontWeight.Bold)
            )

            Row(
                modifier = Modifier
                    .background(color = ColorPallet.neutral200, shape = RoundedCornerShape(16.dp))
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Start level",
                    style = MaterialTheme.typography.body4.copy(ColorPallet.neutral0)
                )

                HorizontalSpacer(value = 4.dp)

                Icon(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(id = R.drawable.ic_cheveron_right),
                    contentDescription = "chevron right",
                    tint = ColorPallet.neutral0,
                )
            }
        }
        VerticalSpacer(value = 16.dp)

        Column{
            Text(
                text = "Description",
                style = MaterialTheme.typography.subtitle3.copy(ColorPallet.neutral900)
            )

            VerticalSpacer(value = 8.dp)

            Text(
                text = "Level with only true or false questions.",
                style = MaterialTheme.typography.body4.copy(ColorPallet.neutral900)
            )
        }
    }
}


@Composable
private fun MultipleChoiceQuestionView(
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .background(color = ColorPallet.neutral40, shape = RoundedCornerShape(16.dp))
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Multiple Choice Level",
                style = MaterialTheme.typography.subtitle1.copy(ColorPallet.neutral900).copy(fontWeight = FontWeight.Bold)
            )

            Row(
                modifier = Modifier
                    .background(color = ColorPallet.neutral200, shape = RoundedCornerShape(16.dp))
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Start level",
                    style = MaterialTheme.typography.body4.copy(ColorPallet.neutral0)
                )

                HorizontalSpacer(value = 4.dp)

                Icon(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(id = R.drawable.ic_cheveron_right),
                    contentDescription = "chevron right",
                    tint = ColorPallet.neutral0,
                )
            }
        }
        VerticalSpacer(value = 16.dp)

        Column{
            Text(
                text = "Description",
                style = MaterialTheme.typography.subtitle3.copy(ColorPallet.neutral900)
            )

            VerticalSpacer(value = 8.dp)

            Text(
                text = "Level with only multiple choice questions. Harder than true or false questions.",
                style = MaterialTheme.typography.body4.copy(ColorPallet.neutral900)
            )
        }
    }
}


@Composable
private fun FreeHandQuestionView(
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .background(color = ColorPallet.neutral40, shape = RoundedCornerShape(16.dp))
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Free Hand Level",
                style = MaterialTheme.typography.subtitle1.copy(ColorPallet.neutral900).copy(fontWeight = FontWeight.Bold)
            )

            Row(
                modifier = Modifier
                    .background(color = ColorPallet.neutral200, shape = RoundedCornerShape(16.dp))
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Start level",
                    style = MaterialTheme.typography.body4.copy(ColorPallet.neutral0)
                )

                HorizontalSpacer(value = 4.dp)

                Icon(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(id = R.drawable.ic_cheveron_right),
                    contentDescription = "chevron right",
                    tint = ColorPallet.neutral0,
                )
            }
        }
        VerticalSpacer(value = 16.dp)

        Column{
            Text(
                text = "Description",
                style = MaterialTheme.typography.subtitle3.copy(ColorPallet.neutral900)
            )

            VerticalSpacer(value = 8.dp)

            Text(
                text = "Level with only free hand questions. The hardest of question types.",
                style = MaterialTheme.typography.body4.copy(ColorPallet.neutral900)
            )
        }
    }
}
