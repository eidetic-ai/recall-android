package ai.eidetic.common.ui.theme

import ai.eidetic.common.R
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

val SoraFontFamily = FontFamily(
    Font(R.font.sora_sb, FontWeight.SemiBold),
)

val RubikFontFamily = FontFamily(
    Font(R.font.rubik_rg, FontWeight.Normal),
    Font(R.font.rubik_sb, FontWeight.SemiBold),
    Font(R.font.rubik_m, FontWeight.Medium),
    Font(R.font.rubik_bd, FontWeight.Bold),
)

/**
 * Defined and naming by: https://www.figma.com/file/0asolgPx1FaMK11kNtVJX4/Handoff-design---Android-1.0?node-id=202%3A1830
 *
 * Letter Spacing Conversion: 1% -> 0.0125em (https://material.io/design/typography/the-type-system.html#applying-the-type-scale)
 */
@OptIn(ExperimentalUnitApi::class)
val Typography = Typography(

    h1 = TextStyle(
        fontFamily = SoraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 30.sp,
        lineHeight = 36.sp,
    ),

    h2 = TextStyle(
        fontFamily = SoraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 26.sp,
        lineHeight = 31.2.sp,
    ),

    h3 = TextStyle(
        fontFamily = RubikFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
    ),

    subtitle1 = TextStyle(
        fontFamily = RubikFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 21.6.sp,
    ),

    subtitle2 = TextStyle(
        fontFamily = RubikFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 19.2.sp,
    ),

    body1 = TextStyle(
        fontFamily = RubikFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 23.4.sp,
    ),

    body2 = TextStyle(
        fontFamily = RubikFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.8.sp,
    ),

    button = TextStyle(
        fontFamily = SoraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 19.2.sp,
    ),

    overline = TextStyle(
        fontFamily = RubikFontFamily,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = (0.125).em,
        lineHeight = 16.sp,
        fontSize = 14.sp,
    ),

    caption = TextStyle(
        fontFamily = RubikFontFamily,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = (-0.0125).em,
        lineHeight = 16.sp,
        fontSize = 12.sp,
    ),
)

val Typography.button1: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = SoraFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 19.2.sp,
        )
    }

val Typography.button2: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = SoraFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 16.8.sp,
        )
    }

val Typography.button3: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = RubikFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 17.5.sp,
        )
    }

val Typography.numbersD1: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = SoraFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 56.sp,
            lineHeight = 67.2.sp,
        )
    }

val Typography.numbers1: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = SoraFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 44.sp,
            lineHeight = 52.8.sp,
        )
    }

val Typography.numbers2: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = SoraFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 34.sp,
            lineHeight = 40.8.sp,
        )
    }

val Typography.numbers3: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = SoraFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            lineHeight = 36.sp,
        )
    }

val Typography.numbers4: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = SoraFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 26.sp,
            lineHeight = 31.2.sp,
        )
    }

val Typography.numbers5: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = SoraFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 24.sp,
        )
    }

val Typography.numbers6: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = SoraFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 19.2.sp,
        )
    }

val Typography.h2Regular: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = RubikFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 26.sp,
            lineHeight = 31.2.sp,
        )
    }

val Typography.subtitle3: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = RubikFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 16.8.sp,
        )
    }

val Typography.subtitle4: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = RubikFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 14.4.sp,
        )
    }

val Typography.subtitle5: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = RubikFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 8.sp,
            lineHeight = 9.6.sp,
        )
    }

val Typography.body3: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = RubikFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 18.2.sp,
        )
    }

val Typography.body4: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = RubikFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 15.6.sp,
        )
    }

val Typography.subtitleD1: TextStyle
    @Composable
    get() = TextStyle(
        fontFamily = SoraFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 21.6.sp,
    )

fun TextStyle.toSpanStyle(): SpanStyle {
    return SpanStyle(
        color = color,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        fontSize = fontSize,
    )
}
