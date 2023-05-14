package ai.eidetic.common.ui.components

import ai.eidetic.common.ui.theme.ColorPallet
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

private const val SPINNER_FRICTION = 42L

@Composable
private fun Spinner(
    modifier: Modifier,
    stroke: Dp,
    color: SpinnerColor,
) {
    var rotation: Float by remember { mutableStateOf(0f) }
    with(LocalDensity.current) {
        val strokeWidth = stroke.toPx()
        Box(
            modifier
                .rotate(rotation)
                .drawBehind {
                    drawArc(
                        color = color.background,
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth)
                    )
                    drawArc(
                        brush = color.foreground,
                        startAngle = 270f,
                        sweepAngle = 144f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                }
        )
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(SPINNER_FRICTION)
            rotation = (rotation + 22.5f).mod(360f)
        }
    }
}

@Composable
fun SpinnerLight(modifier: Modifier = Modifier, size: Dp = 48.dp, stroke: Dp = 6.dp) {
    Spinner(modifier = modifier.size(size), color = SpinnerColor.Light, stroke = stroke)
}

private interface SpinnerColor {
    val background: Color
    val foreground: Brush

    object Light : SpinnerColor {
        override val background = ColorPallet.neutral40
        override val foreground = Brush.verticalGradient(
            listOf(
                ColorPallet.neutral400,
                ColorPallet.neutral40.copy(alpha = 0.5f)
            )
        )
    }
}
