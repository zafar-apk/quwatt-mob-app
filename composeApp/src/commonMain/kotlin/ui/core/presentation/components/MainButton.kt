package ui.core.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.domain.util.stringResource
import dev.icerock.moko.resources.StringResource
import tj.ham_safar.app.android.theme.Yellow

@Deprecated("Use moko resources instead. Label is not gonna work otherwise")
@Composable
fun MainButton(
    labelRes: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Yellow,
    contentColor: Color = Color.Black,
    onClick: () -> Unit
) {
    MainButtonInternal(
        label = stringResource(labelRes),
        modifier = modifier,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        onClick = onClick
    )
}

@Composable
fun MainButton(
    labelResource: StringResource,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Yellow,
    contentColor: Color = Color.Black,
    onClick: () -> Unit
) {
    MainButtonInternal(
        label = stringResource(labelResource),
        modifier = modifier,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        onClick = onClick
    )
}


// rename once the list of usages of deprecated one is empty
@Composable
fun MainButtonInternal(
    label: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Yellow,
    contentColor: Color = Color.Black,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .height(50.dp)
            .shadow(elevation = 4.dp),
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            color = contentColor
        )
    }
}


//@Preview
//@Composable
//fun MainButtonPreview() {
//    HamSafarTheme {
//        MainButton(
//            modifier = Modifier.fillMaxWidth(),
//            labelRes = R.string.next,
//            onClick = {}
//        )
//    }
//}