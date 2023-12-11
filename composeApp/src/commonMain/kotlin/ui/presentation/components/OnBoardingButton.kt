package ui.presentation.components

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.domain.util.stringResource

@Composable
fun OnBoardingButton(
    modifier: Modifier = Modifier,
    labelRes: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .shadow(elevation = 4.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = White
        )
    ) {
        Text(
            text = stringResource(id = labelRes),
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center
        )
    }
}

//@Preview
//@Composable
//private fun OnBoardingButtonPreview() {
//    HamSafarTheme {
//        OnBoardingButton(
//            Modifier.fillMaxWidth(),
//            labelRes = R.string.next
//        ) {
//
//        }
//    }
//}