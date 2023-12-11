package tj.ham_safar.app.android.register.user.presentation.user.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import tj.ham_safar.app.android.theme.Blue
import tj.ham_safar.app.android.theme.Gray

@Composable
fun LineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier,
    isEditable: Boolean = true
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.subtitle1,
        enabled = isEditable,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            imeAction = imeAction,
            keyboardType = KeyboardType.Ascii
        ),
        keyboardActions = keyboardActions,
        decorationBox = { textField ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.subtitle1,
                    color = Gray
                )
                Spacer(modifier = Modifier.size(2.dp))
                textField()
                Divider(color = Blue)
            }
        }
    )
}