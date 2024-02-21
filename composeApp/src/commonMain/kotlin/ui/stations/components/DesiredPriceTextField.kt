package tj.ham_safar.app.android.trips.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DesiredPriceTextField(
    price: Int,
    modifier: Modifier = Modifier,
    prefix: String? = null,
    currency: String? = null,
    imeAction: ImeAction = ImeAction.Next,
    onPriceChanged: (price: Int) -> Unit,
) {

    BasicTextField(
        value = price.toString(),
        onValueChange = {
            if (it.isEmpty()) onPriceChanged(0)
            val number = it.toIntOrNull()
            if (number != null) onPriceChanged(number)

        },
        singleLine = true,
        textStyle = TextStyle(fontSize = 20.sp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        cursorBrush = SolidColor(MaterialTheme.colors.primary),

        ) { innerTextField ->

        Card(shape = MaterialTheme.shapes.medium) {
            Row(
                modifier = Modifier.padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (prefix != null)
                    Text(
                        style = MaterialTheme.typography.h2,
                        text = prefix,
                        textAlign = TextAlign.End,
                    )

                Column(
                    modifier = modifier.padding(horizontal = 6.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    innerTextField()
                    if (prefix.isNullOrBlank().not() || currency.isNullOrBlank().not())
                        Divider(color = Color.Black, thickness = 1.dp)
                }
                if (currency != null)
                    Text(
                        style = MaterialTheme.typography.h2,
                        text = currency,
                    )
            }
        }
    }
}

//@Preview
//@Composable
//fun DesiredPricePreview() {
//    HamSafarTheme {
//        DesiredPriceTextField(
//            price = 5,
//            onPriceChanged = {},
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(68.dp)
//        )
//    }
//}
