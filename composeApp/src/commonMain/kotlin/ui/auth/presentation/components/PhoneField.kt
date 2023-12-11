package ui.auth.presentation.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import core.domain.util.stringResource
import ui.theme.TextBlack

@Composable
fun PhoneField(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    phone: MutableState<String>
) {
    val isPhoneError by remember {
        mutableStateOf(false)
    }

    TextField(
        value = phone.value,
        onValueChange = {
            phone.value = it
        },
        label = {
            val textRes =
                if (isPhoneError) "phone_error" else "phone_prefix"
            val color = if (isPhoneError) {
                MaterialTheme.colors.error
            } else {
                MaterialTheme.colors.onSurface
            }
            Text(stringResource(id = textRes), color = color)
        },
        singleLine = true,
        isError = isPhoneError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusRequester.requestFocus()
            }
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            unfocusedLabelColor = TextBlack,
            unfocusedIndicatorColor = Color.Transparent,
            disabledLabelColor = TextBlack,
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        modifier = modifier
    )
}

//@Preview
//@Composable
//fun PhoneFieldPreview() {
//    HamSafarTheme {
//        PhoneField(
//            focusRequester = FocusRequester(),
//            phone = remember {
//                mutableStateOf("")
//            }
//        )
//    }
//}