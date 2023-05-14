package ai.eidetic.common.ui.components

import ai.eidetic.common.ui.theme.ColorPallet
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun TextField(
    onTextChanged: (String) -> Unit,
    label: String,
    placeholder: String,
    isPassword: Boolean = false,
) {
    var text by remember { mutableStateOf(TextFieldValue()) }

    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
        ,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedLabelColor = ColorPallet.primary900,
            focusedLabelColor = ColorPallet.primary600,
            unfocusedBorderColor = ColorPallet.primary900,
            focusedBorderColor = ColorPallet.primary600,
            cursorColor = ColorPallet.neutral900,
            textColor = ColorPallet.neutral900,
            placeholderColor = ColorPallet.neutral900
        ),
        value = text,
        onValueChange = {
            text = it
            onTextChanged(it.text)
        },
        label = {
            Text(

                text = label
            )
        },
        placeholder = {
            Text(text = placeholder)
        },
        visualTransformation = if(isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}