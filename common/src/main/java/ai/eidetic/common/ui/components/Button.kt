package ai.eidetic.common.ui.components

import ai.eidetic.common.ui.theme.ColorPallet
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Button(
    modifier: Modifier = Modifier,
    text: String,
    padding: PaddingValues = PaddingValues(horizontal = 24.dp),
    isLoading: Boolean = false,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
    backgroundColorDefault: Color = ColorPallet.neutral500,
) {
    val backgroundColor by animateColorAsState(targetValue = if (isLoading.not() && isEnabled) backgroundColorDefault else ColorPallet.neutral100)
    val contentColor by animateColorAsState(targetValue = if (isLoading.not() && isEnabled) ColorPallet.neutral0 else ColorPallet.neutral40)

    Row(
        modifier = modifier
            .height(56.dp)
            .padding(padding)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                if (isLoading.not() && isEnabled) onClick()
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isLoading) {
            ButtonSpinner()

            HorizontalSpacer(value = 8.dp)
        }

        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            text = text,
            style = MaterialTheme.typography.button.copy(color = contentColor),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ButtonSpinner(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.size(24.dp),
        contentAlignment = Alignment.Center
    ) {
        SpinnerLight(stroke = 3.dp)
    }
}
