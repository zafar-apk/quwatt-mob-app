package ui.core.presentation.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ui.theme.BackgroundGray
import ui.core.presentation.components.LoginView

@Composable
fun RequestAuthorizationDialog(
    onLoginClicked: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        LoginView(
            modifier = Modifier.fillMaxHeight(0.7F)
                .background(BackgroundGray, RoundedCornerShape(size = 16.dp)),
            onLoginClicked = {
                onLoginClicked()
                onDismissRequest()
            },
            onCancelClicked = {
                onDismissRequest()
            }
        )
    }
}

//@Preview
//@Composable
//private fun DialogPreview() {
//    HamSafarTheme {
//        RequestAuthorizationDialog(
//            dialogState = rememberMaterialDialogState(initialValue = true),
//            onLoginClicked = {}
//        )
//    }
//}