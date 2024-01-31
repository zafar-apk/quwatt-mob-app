package ui.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import tj.quwatt.quwattapp.SharedRes
import ui.theme.Primary

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoginView(
    onLoginClicked: () -> Unit,
    modifier: Modifier = Modifier,
    onCancelClicked: (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 18.dp),
            textAlign = TextAlign.Center,
            text = stringResource(SharedRes.strings.need_auth_title),
            style = MaterialTheme.typography.subtitle1.copy(fontSize = 24.sp),
            color = Color.Black
        )

        Image(
            modifier = Modifier
                .size(width = 245.dp, height = 147.dp)
                .padding(horizontal = 18.dp),
            painter = painterResource(SharedRes.images.illustration_login),
            contentDescription = stringResource(SharedRes.strings.illustration_login)
        )

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MainButton(
                modifier = Modifier
                    .fillMaxWidth(),
                labelResource = SharedRes.strings.login,
                onClick = onLoginClicked
            )

            onCancelClicked?.let {
                MainButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    labelResource = SharedRes.strings.cancel_uppercase,
                    backgroundColor = Color.White,
                    contentColor = Primary,
                    onClick = onCancelClicked
                )
            }
        }
    }
}
