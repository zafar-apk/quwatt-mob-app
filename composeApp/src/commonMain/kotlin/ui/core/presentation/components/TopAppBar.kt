package tj.ham_safar.app.android.core.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.core.presentation.components.BackButton

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    text: String = "",
    backButtonEnabled: Boolean = true,
    onBackButtonClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp)
            .padding(top = 21.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (backButtonEnabled) {
            BackButton { onBackButtonClick() }
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = text,
            style = MaterialTheme.typography.body1.copy(fontSize = 20.sp)
        )
    }
}