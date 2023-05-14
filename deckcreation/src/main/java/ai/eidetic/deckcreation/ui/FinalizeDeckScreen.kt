package ai.eidetic.deckcreation.ui

import ai.eidetic.common.state.deckCreationState.DeckCreationViewStore
import ai.eidetic.common.ui.components.Button
import ai.eidetic.common.ui.components.HorizontalSpacer
import ai.eidetic.common.ui.components.SpinnerLight
import ai.eidetic.common.ui.components.VerticalSpacer
import ai.eidetic.common.ui.theme.ColorPallet
import ai.eidetic.domain.generatelessons.model.MetadataDeckModel
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.koin.androidx.compose.getViewModel
import java.io.File
import java.util.Calendar
import java.util.Date

@Composable
fun FinalizeDeckScreen(
    navController: NavHostController,
    onFinishClicked: () -> Unit = {},
    file: File,
    viewStore: DeckCreationViewStore = getViewModel(),
) {
    BackHandler {}

    val state = viewStore()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopAppBar(
            backgroundColor = ColorPallet.neutral300,
            contentColor = ColorPallet.neutral0,
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Deck Finalization",
                    style = MaterialTheme.typography.subtitle1.copy(color = ColorPallet.neutral0),
                    textAlign = TextAlign.Center
                )
            }
        }
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (state.deck.sections.isEmpty()) {
                    SpinnerLight(modifier = Modifier.size(60.dp))
                }
                VerticalSpacer(value = 32.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Deck title:",
                        style = MaterialTheme.typography.h6.copy(color = ColorPallet.neutral900)
                    )

                    HorizontalSpacer(value = 12.dp)

                    TitleInput(
                        title = state.deck.metadata.name,
                        onTitleChanged = { viewStore.changeDeckTitle(it) }
                    )
                }


                VerticalSpacer(value = 12.dp)

                DatePicker(
                    data = state.deck.metadata,
                    onDateSelected = { viewStore.changeDeckDueDate(it) }
                )

                VerticalSpacer(value = 12.dp)

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    text = "Generated lessons:",
                    style = MaterialTheme.typography.h6.copy(color = ColorPallet.neutral900)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    items(state.deck.sections) {
                        VerticalSpacer(value = 24.dp)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = ColorPallet.neutral30, shape = RoundedCornerShape(16.dp))
                                .padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Topic:",
                                style = MaterialTheme.typography.subtitle2.copy(color = ColorPallet.neutral900)
                            )

                            HorizontalSpacer(value = 8.dp)

                            Text(
                                text = it.topic,
                                style = MaterialTheme.typography.subtitle2.copy(color = ColorPallet.neutral900)
                            )
                        }
                    }
                    
                    item { 
                        VerticalSpacer(value = 100.dp)
                    }
                }
            }

            Button(
                modifier= Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
                text = "Start Learning",
                onClick = {
                    viewStore.saveData(file, state.deck)
                    onFinishClicked()
                }
            )
        }
    }
}

@Composable
fun TitleInput(
    modifier: Modifier = Modifier,
    title: String,
    onTitleChanged: (String) -> Unit
) {
    BasicTextField(
        modifier = modifier,
        value = title,
        onValueChange = onTitleChanged,
        textStyle = MaterialTheme.typography.subtitle1.copy(color = ColorPallet.neutral900),
    )

}

@Composable
fun DatePicker(
    data: MetadataDeckModel,
    onDateSelected: (String) -> Unit
) {

    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            onDateSelected("$mYear-${mMonth + 1}-$mDay")
        }, mYear, mMonth, mDay
    )

    mDatePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
        onDateSelected("$year-${month + 1}-$dayOfMonth")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Deck due date:",
            style = MaterialTheme.typography.h6.copy(color = ColorPallet.neutral900)
        )

        HorizontalSpacer(value = 12.dp)


        Text(
            text = data.due,
            modifier = Modifier.clickable {
                mDatePickerDialog.show()
            },
            style = MaterialTheme.typography.subtitle1.copy(color = ColorPallet.neutral900),
        )
    }
}

