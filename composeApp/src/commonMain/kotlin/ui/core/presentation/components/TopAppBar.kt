package ui.core.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    text: String = "",
    backButtonEnabled: Boolean = true,
    onBackButtonClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        if (backButtonEnabled) {
            BackButton { onBackButtonClick() }
        }
        Text(
            modifier = Modifier.weight(1F),
            textAlign = TextAlign.Center,
            text = text,
            style = MaterialTheme.typography.body1.copy(fontSize = 20.sp)
        )
        content()
    }
}