package ui.trips.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.domain.util.stringResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.core.presentation.painterResource
import tj.ham_safar.app.android.theme.Yellow


@OptIn(ExperimentalResourceApi::class)
@Composable
fun CounterButton(
    modifier: Modifier = Modifier,
    counterIcon: CounterIcon = CounterIcon.POSITIVE,
    backgroundColor: Color = Yellow,
    contentColor: Color = Color.White,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(6.dp),
        shape = MaterialTheme.shapes.medium,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        ),
    ) {
        Icon(
            painter = painterResource(counterIcon.icon),
            contentDescription = stringResource(id = counterIcon.description),
            tint = Color.Unspecified
        )
    }
}

enum class CounterIcon(
    val icon: String,
    val description: String
) {
    POSITIVE("ic_positive.xml", "increase_button"),
    NEGATIVE("ic_negative.xml", "decrease_button")
}

//@Preview
//@Composable
//fun CounterButtonPreview() {
//    HamSafarTheme {
//        CounterButton(
//            Modifier.size(46.dp),
//            counterIcon = CounterIcon.NEGATIVE
//        ) {}
//    }
//}