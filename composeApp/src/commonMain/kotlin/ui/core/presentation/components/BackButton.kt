package ui.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import tj.quwatt.quwattapp.SharedRes

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Icon(
        modifier = modifier.clickable { onClick() },
        painter = painterResource(SharedRes.images.arrow_left),
        contentDescription = stringResource(SharedRes.strings.back_button)
    )
}