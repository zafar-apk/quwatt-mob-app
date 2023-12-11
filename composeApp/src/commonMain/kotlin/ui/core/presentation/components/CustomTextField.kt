package tj.ham_safar.app.android.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import tj.ham_safar.app.android.core.presentation.withIf
import ui.theme.GrayGainsboro
import ui.theme.Yellow
import ui.core.presentation.painterResource


@OptIn(ExperimentalResourceApi::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit = {},
    isPasswordField: Boolean = false,
    placeholderText: String = "",
    fontSize: TextUnit = 15.sp,
    maxCount: Int = 21,
    isError: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions { },
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Phone
    ),
) {
    val textStyle = LocalTextStyle.current.copy(fontSize = fontSize)
    val shape = MaterialTheme.shapes.medium
    var text by rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    val visualTransformation = when {
        isPasswordField && passwordVisibility -> VisualTransformation.None
        isPasswordField && !passwordVisibility -> PasswordVisualTransformation()
        !isPasswordField -> VisualTransformation.None
        else -> VisualTransformation.None
    }

//    val image = if (passwordVisibility) Icons.Filled.VisibilityOff
//    else Icons.Filled.Visibility

    BasicTextField(
        modifier = modifier
            .height(53.dp)
            .background(
                color = Color.White,
                shape = shape
            )
            .border(
                width = 0.5.dp,
                color = if (isError) Color.Red else GrayGainsboro,
                shape = shape
            )
            .padding(vertical = 4.dp, horizontal = 16.dp),
        value = text,
        onValueChange = {
            if (it.length <= maxCount) {
                onTextChanged(it)
                text = it
            }
        },
        singleLine = true,
        cursorBrush = SolidColor(Yellow),
        textStyle = textStyle,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        decorationBox = { innerTextField ->
            Row(
                modifier
                    .withIf(isPasswordField) {
                        padding(end = 8.dp)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    placeholderText,
                    style = textStyle
                )

                Spacer(modifier = Modifier.padding(2.dp))

                Box(Modifier.weight(1f)) {
                    innerTextField()
                }

                if (isPasswordField) {
                    Icon(
                        modifier = Modifier
                            .clickable {
                                passwordVisibility = !passwordVisibility
                            },
//                        imageVector = image,
                        // TODO: replace icon
                        painter = painterResource("ic_orders.xml"),
                        contentDescription = null
                    )
                }
            }
        }
    )
}

//@Preview
//@Composable
//fun CustomTextField_preview() {
//    CustomTextField()
//}