package ui.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import tj.quwatt.quwattapp.SharedRes
import ui.core.presentation.painterResource
import ui.theme.Gray

@Composable
fun ErrorView(
    error: String,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 21.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1F))

        Image(
            painter = painterResource("ic_sad.xml"),
            contentDescription = stringResource(SharedRes.strings.sad_icon)
        )

        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(SharedRes.strings.error),
            style = MaterialTheme.typography.h2
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = error,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h3,
        )

        Spacer(modifier = Modifier.weight(1F))

        Text(
            text = stringResource(SharedRes.strings.descriptioin_troubleshooting),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = Gray
        )

        MainButton(
            labelResource = SharedRes.strings.retry,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp),
            onClick = { onRetry?.invoke() }
        )

        Spacer(modifier = Modifier.size(24.dp))
    }
}

//@Preview
//@Composable
//fun ErrorViewPreview() {
//    HamSafarTheme {
//        Surface(color = BackgroundGray) {
//            ErrorView(error = "Не удалось соединиться с сервером, проверьте интернет соединение.")
//        }
//    }
//}