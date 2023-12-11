package ui.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import core.domain.util.stringResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.core.presentation.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Icon(
        modifier = modifier.clickable { onClick() },
        painter = painterResource("arrow_left.xml"),
        contentDescription = stringResource(id = "back_button"),
        tint = Color.Unspecified
    )
}

//@Preview
//@Composable
//fun BackButtonPreview() {
//    BackButton(onClick = {})
//}