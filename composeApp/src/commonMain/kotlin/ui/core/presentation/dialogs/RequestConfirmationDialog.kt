package ui.core.presentation.dialogs

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.domain.util.stringResource
import ui.theme.Blue

@Composable
fun RequestConfirmationDialog(
    text: String? = null,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {

    AlertDialog(
        onDismissRequest = { },
        text = text?.let {
            {
                Text(
                    textAlign = TextAlign.Center,
                    text = text,
                    style = MaterialTheme.typography.subtitle1.copy(fontSize = 22.sp),
                    color = Color.Black
                )
            }
        },
        buttons = {
            Row(modifier = Modifier.padding(horizontal = 4.dp)) {
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                    onClick = onCancel,
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                ) {
                    Text(
                        text = stringResource(id = "cancel"),
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.subtitle1
                    )
                }

                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                    onClick = onConfirm,
                    colors = ButtonDefaults.textButtonColors(contentColor = Blue)
                ) {
                    Text(
                        text = stringResource(id = "confirm"),
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.subtitle1
                    )
                }

            }
        }
    )
}

//@Preview
//@Composable
//private fun DialogPreview() {
//    HamSafarTheme {
//        RequestConfirmationDialog(
//            text = "Confirm your activities",
//            onCancel = {},
//            onConfirm = {}
//        )
//    }
//}