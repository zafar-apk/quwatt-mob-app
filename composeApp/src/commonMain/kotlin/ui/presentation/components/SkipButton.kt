package ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.domain.util.stringResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.core.presentation.painterResource
import ui.theme.Primary

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SkipButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .background(
                color = Color.White,
                shape = CircleShape
            )
            .padding(horizontal = 5.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = "skip"),
            style = MaterialTheme.typography.body1.copy(color = Primary)
        )

        Spacer(modifier = Modifier.size(6.dp))

        Icon(
            painter = painterResource("arrow_right.xml"),
            contentDescription = stringResource(id = "skip"),
            tint = Color.Unspecified
        )
    }
}

//@Composable
//@Preview
//fun SkipButtonPreview() {
//    HamSafarTheme {
//        SkipButton {
//
//        }
//    }
//}