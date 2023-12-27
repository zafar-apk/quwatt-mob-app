package core.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import kotlinx.coroutines.delay
import ui.theme.TextBlack

@Composable
fun Toast(
    messageState: MutableState<String?>,
    lengthSeconds: Long = 2
) {
    LaunchedEffect(messageState.value) {
        delay(lengthSeconds * 1_000)
        messageState.value = null
    }

    AnimatedVisibility(
        visible = messageState.value != null,
        modifier = Modifier.fillMaxSize()
    ) {
        Popup(
            alignment = Alignment.BottomCenter,
            onDismissRequest = { messageState.value = null }
        ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(bottom = 64.dp)
            ) {
                Text(
                    modifier = Modifier.clip(MaterialTheme.shapes.medium)
                        .background(TextBlack.copy(alpha = 0.7F))
                        .padding(16.dp)
                        .align(Alignment.BottomCenter),
                    text = messageState.value ?: return@Box,
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                )
            }
        }
    }

}