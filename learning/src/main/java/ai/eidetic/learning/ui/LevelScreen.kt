package ai.eidetic.learning.ui

import ai.eidetic.common.state.learning.LearningViewStore
import ai.eidetic.common.state.learning.LevelType
import ai.eidetic.common.ui.components.Button
import ai.eidetic.common.ui.components.HorizontalSpacer
import ai.eidetic.common.ui.components.VerticalSpacer
import ai.eidetic.common.ui.theme.ColorPallet
import ai.eidetic.domain.generatelessons.model.CardModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.getViewModel
import kotlin.random.Random

@Composable
fun LevelScreen(
    navController: NavHostController,
    viewStore: LearningViewStore = getViewModel(),
) {
    val state = viewStore()
    val selectedDeck = state.decks.firstOrNull() { it.id == state.selectedDeckID } ?: return

    val cards = selectedDeck.sections.map { it.cards }.flatten()
    val trueStatements = cards.flatMap { listOf(it.trueStatement) }
    val statements = cards.flatMap { it.false_statements + listOf(it.trueStatement) }

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
                        text = getTitle(state.type),
                        style = MaterialTheme.typography.subtitle1.copy(color = ColorPallet.neutral0),
                        textAlign = TextAlign.Center
                    )
                }
            }

            when (state.type) {
                LevelType.TRUE_FALSE -> TrueFalseView(statements, viewStore, trueStatements)
                LevelType.MULTIPLE_CHOICE -> MultipleChoiceView(viewStore, cards)
                LevelType.FREE_HAND -> FreeHandView(viewStore, cards)
                LevelType.NOT_SELECTED -> Text("Error occured")
            }


        }
    }
}

@Composable
private fun TrueFalseView(
    statements: List<String>,
    viewStore: LearningViewStore,
    trueStatements: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        VerticalSpacer(value = 24.dp)


        statements.forEach { statement ->
            var correct by remember { mutableStateOf<Boolean?>(null) }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .background(
                        if (correct == true) ColorPallet.systemSuccess100 else if (correct == false) ColorPallet.systemError100 else ColorPallet.neutral40,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(12.dp)
            ) {

                Text(
                    text = statement,
                    style = MaterialTheme.typography.subtitle1.copy(ColorPallet.neutral900),
                    textAlign = TextAlign.Center
                )

                VerticalSpacer(value = 24.dp)

                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        modifier = Modifier.weight(1.0f),
                        padding = PaddingValues(0.dp),
                        text = "True",
                        onClick = {
                            correct = viewStore.checkIfTrue(
                                trueStatements = trueStatements,
                                statement = statement,
                                check = true,
                            )
                        }
                    )

                    HorizontalSpacer(value = 24.dp)

                    Button(
                        modifier = Modifier.weight(1.0f),
                        padding = PaddingValues(0.dp),
                        text = "False",
                        onClick = {
                            correct = viewStore.checkIfTrue(
                                trueStatements = trueStatements,
                                statement = statement,
                                check = false,
                            )
                        }
                    )
                }
            }

            VerticalSpacer(value = 24.dp)
        }
    }
}

@Composable
private fun FreeHandView(
    viewStore: LearningViewStore,
    cards: List<CardModel>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        VerticalSpacer(value = 24.dp)


        cards.forEach { card ->
            var correct by remember { mutableStateOf<Boolean?>(null) }
            var answer by remember { mutableStateOf<String>("Write your answer here") }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .background(
                        if (correct == true) ColorPallet.systemSuccess100 else if (correct == false) ColorPallet.systemError100 else ColorPallet.neutral40,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = card.question,
                    style = MaterialTheme.typography.subtitle1.copy(ColorPallet.neutral900),
                    textAlign = TextAlign.Center
                )

                VerticalSpacer(value = 24.dp)

                AnswerInput(title = answer, onAnswerChanged = {answer = it})

                VerticalSpacer(value = 24.dp)

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    padding = PaddingValues(0.dp),
                    text = "Check",
                    onClick = {
                        correct = Random.nextInt() % 2 == 0
                    }
                )
            }

            VerticalSpacer(value = 24.dp)
        }
    }
}

@Composable
fun AnswerInput(
    modifier: Modifier = Modifier,
    title: String,
    onAnswerChanged: (String) -> Unit
) {
    BasicTextField(
        modifier = modifier
            .background(ColorPallet.neutral30, RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 4.dp),
        value = title,
        onValueChange = onAnswerChanged,
        textStyle = MaterialTheme.typography.subtitle1.copy(color = ColorPallet.neutral900),
    )
}

@Composable
private fun MultipleChoiceView(
    viewStore: LearningViewStore,
    cards: List<CardModel>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        VerticalSpacer(value = 24.dp)


        cards.forEach { card ->
            var correct by remember { mutableStateOf<Boolean?>(null) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .background(
                        if (correct == true) ColorPallet.systemSuccess100 else if (correct == false) ColorPallet.systemError100 else ColorPallet.neutral40,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = card.question,
                    style = MaterialTheme.typography.subtitle1.copy(ColorPallet.neutral900),
                    textAlign = TextAlign.Center
                )

                VerticalSpacer(value = 24.dp)
                val choiceFinal = card.choices + listOf(card.answer)

                choiceFinal.forEach { choice ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .background(ColorPallet.neutral10, RoundedCornerShape(12.dp))
                            .padding(12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                correct = viewStore.checkChoices(card, choice)
                            }
                    ) {
                        Text(
                            text = choice
                        )
                    }

                    VerticalSpacer(value = 12.dp)
                }
            }

            VerticalSpacer(value = 24.dp)
        }
    }
}

fun getTitle(type: LevelType) = when (type) {
    LevelType.TRUE_FALSE -> "True or false level"
    LevelType.MULTIPLE_CHOICE -> "Multiple choice level"
    LevelType.FREE_HAND -> "Free hand level"
    LevelType.NOT_SELECTED -> "Undefined level"
}

