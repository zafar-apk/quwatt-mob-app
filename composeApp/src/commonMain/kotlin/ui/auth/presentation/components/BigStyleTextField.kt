package ui.auth.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.theme.GrayGainsboro
import ui.theme.LightGray
import ui.theme.Red
import ui.theme.TextBlack

@Composable
internal fun BigStyleTextField(
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit = {},
    value: String = "",
    label: String? = null,
    maxLength: Int = 21,
    leadingIcon: @Composable (() -> Unit)? = null,
    ignoreFocusProcessing: Boolean = false,
    phonePrefix: String? = null,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    enabled: Boolean = true,
    isError: Boolean = false,
    hint: String = "",
    fontSize: TextUnit = 15.sp,
    textAlignment: TextAlign = TextAlign.Start
) {

    var labelState by remember {
        mutableStateOf(label)
    }

    TextField(
        modifier = modifier
            .border(
                width = 1.dp,
                color = if (isError) Red else GrayGainsboro,
                shape = MaterialTheme.shapes.medium
            )
            .onFocusChanged { state ->
                if (ignoreFocusProcessing) return@onFocusChanged
                if (state.isFocused) labelState = null
                else if (value.isNotEmpty()) labelState = label
            },
        value = value,
        onValueChange = {
            if (it.length <= maxLength) {
                onValueChanged(it)
            }
        },
        enabled = enabled,
        label = labelState?.let { { Text(text = it, color = GrayGainsboro) } },
        singleLine = singleLine,
        shape = MaterialTheme.shapes.medium,
        visualTransformation = visualTransformation,
        isError = isError,
        textStyle = TextStyle(
            color = TextBlack,
            fontSize = fontSize,
            textAlign = textAlignment
        ),
        leadingIcon = when {
            phonePrefix != null -> PhonePrefix(phonePrefix)
            leadingIcon != null -> leadingIcon
            else -> null
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        placeholder = {
            Text(
                text = hint,
                style = MaterialTheme.typography.h3,
                color = LightGray,
                textAlign = textAlignment
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedLabelColor =
            if (ignoreFocusProcessing) TextBlack
            else Color.Transparent,
            backgroundColor = White,
            unfocusedLabelColor = TextBlack,
            unfocusedIndicatorColor = Color.Transparent,
            disabledLabelColor = TextBlack,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        )
    )
}

@Composable
private fun PhonePrefix(phonePrefix: String): @Composable (() -> Unit) = {
    Text(
        modifier = Modifier.padding(start = 8.dp, top = 2.dp),
        text = phonePrefix,
        color = TextBlack,
        fontSize = 15.sp
    )
}

//@Preview
//@Composable
//fun BigTextStyleTextField_preview() {
//    BigStyleTextField(
//        value = "",
//        label = "Text field",
//        enabled = false,
//        hint = "09/05/2023"
//    )
//}
