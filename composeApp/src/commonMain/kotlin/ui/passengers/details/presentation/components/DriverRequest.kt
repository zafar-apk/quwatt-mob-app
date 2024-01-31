package ui.passengers.details.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.domain.util.stringResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import profile.domain.User
import ui.core.presentation.getImagePainterOrPlaceHolder
import ui.core.presentation.painterResource
import ui.theme.Blue
import ui.theme.PrimaryGray
import ui.theme.propertyText

@Composable
fun DriverRequest(
    user: User,
    modifier: Modifier = Modifier,
    onAccept: (() -> Unit)? = null,
    onDecline: (() -> Unit)? = null
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = PrimaryGray
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .padding(top = 10.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    modifier = Modifier.size(width = 112.dp, height = 70.dp),
                    painter = getImagePainterOrPlaceHolder(
//                        photo = user.transport?.photo,
                        photo = "",
                        placeholderResId = "car_placeholder@1x.png"
                    ),
                    contentDescription = stringResource(id = "transport_photo")
                )

                Spacer(modifier = Modifier.size(5.dp))

                Column {
                    Text(text = user.getFullName(), style = propertyText)

                    Spacer(modifier = Modifier.size(8.dp))

//                    RatingBar(rating = user.rating)
                }
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                DriverActionButton(
                    modifier = Modifier.weight(1F),
                    onClick = onAccept,
                    iconRes = "ic_check.xml",
                    labelRes = "accept",
                    background = Blue,
                    contentColor = Color.White
                )

                Spacer(modifier = Modifier.size(6.dp))

                DriverActionButton(
                    modifier = Modifier.weight(1F),
                    onClick = onDecline,
                    iconRes = "ic_close.xml",
                    labelRes = "decline",
                    background = Color.White,
                    contentColor = Color.Black
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun DriverActionButton(
    iconRes: String,
    labelRes: String,
    background: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Button(
        modifier = modifier,
        onClick = { onClick?.invoke() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = background,
            contentColor = contentColor
        )
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = stringResource(id = labelRes)
        )

        Spacer(modifier = Modifier.size(4.dp))

        Text(
            text = stringResource(id = labelRes),
            style = MaterialTheme.typography.body1
        )
    }
}

//@Preview
//@Composable
//fun DriverRequestPreview() {
//    HamSafarTheme {
//        DriverRequest(
//            user = User(
//                id = 0,
//                phone = "",
//                photo = null,
//                name = "Мурод",
//                surname = "Холматов",
//                patronymic = "Ахадович",
//                dateOfBirth = "22/12/1997",
//                isDriver = true,
//                transport = null,
//                licenceNumber = null,
//                licenceExpiration = null,
//                passportNumber = null, rating = 3.0
//
//            )
//        )
//    }
//}