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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.domain.util.stringResource
import ui.theme.Blue
import ui.theme.Gray
import ui.core.presentation.painterResource

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
            contentDescription = stringResource(id = "sad_icon")
        )

        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(id = "error"),
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
            text = stringResource(id = "descriptioin_troubleshooting"),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = Gray
        )

        MainButton(
            labelRes = "retry",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp),
            backgroundColor = Blue,
            contentColor = Color.White,
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