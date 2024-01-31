package ui.stations.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import core.domain.util.stringResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.core.presentation.painterResource
import ui.theme.TextBlack
import ui.theme.Primary
import tj.ham_safar.app.android.trips.components.DashLine
import tj.ham_safar.app.android.trips.components.PassengersCount

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BookedTripItem(
    fromAddress: String,
    fromCity: String,
    tripTime: String,
    tripDate: String,
    toCity: String,
    toAddress: String,
    price: Int,
    seatAmount: Int,
    availableSeats: Int,
    rating: Double,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Card(modifier = modifier.clickable { onClick() }, backgroundColor = Primary) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(vertical = 8.dp)
        ) {
            Column {
                AddressRow(
                    modifier = Modifier.padding(start = 11.dp),
                    iconColor = Color.White,
                    text = "${fromCity}, $fromAddress"
                )
                Row(
                    modifier = Modifier.height(IntrinsicSize.Max),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DashLine(modifier = Modifier.padding(start = 19.5.dp))
                    Image(
                        painter = painterResource("car_placeholder@1x.png"),
                        contentDescription = stringResource(id = "car_placeholder"),
                        modifier = Modifier
                            .width(80.dp)
                            .height(50.dp)
                            .padding(start = 14.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 5.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = tripTime,
                            color = Color.White,
                            style = LocalTextStyle.current.merge(
                                MaterialTheme.typography.body2.copy(
//                                    platformStyle = PlatformTextStyle(includeFontPadding = false),
                                    lineHeightStyle = LineHeightStyle(
                                        alignment = LineHeightStyle.Alignment.Center,
                                        trim = LineHeightStyle.Trim.Both
                                    )
                                )
                            ),
                        )
                        RatingBar(rating = rating, starsColor = Color.White)
                        Text(
                            text = stringResource(
                                id = "price",
                                listOf(price)
                            ),
                            style = LocalTextStyle.current.merge(
                                MaterialTheme.typography.body2.copy(
                                    color = Color.Black,
//                                    platformStyle = PlatformTextStyle(includeFontPadding = false),
                                    lineHeightStyle = LineHeightStyle(
                                        alignment = LineHeightStyle.Alignment.Center,
                                        trim = LineHeightStyle.Trim.Both
                                    )
                                )
                            ),
                            modifier = Modifier
                                .background(
                                    color = Color.White,
                                    MaterialTheme.shapes.medium
                                )
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                        )
                    }
                }
                AddressRow(
                    modifier = Modifier.padding(start = 12.dp),
                    iconColor = TextBlack,
                    text = "$toCity, $toAddress"
                )
            }
            Spacer(modifier = Modifier.weight(1F))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 9.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = tripDate,
                    color = Color.White,
                    style = MaterialTheme.typography.body2
                )
                PassengersCount(
                    progress = (seatAmount - availableSeats).toFloat() / seatAmount.toFloat(),
                    text = "${seatAmount - availableSeats}/${seatAmount}"
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun BookTripItemPreview() {
//    HamSafarTheme {
//        BookedTripItem(
//            fromAddress = "Автовокзал",
//            fromCity = "Худжанд",
//            tripTime = "12:00",
//            tripDate = "02.02.23",
//            toCity = "Душанбе",
//            toAddress = "Водонасос",
//            price = 150,
//            seatAmount = 4,
//            availableSeats = 3,
//            rating = 1.0
//        ) {
//            // NO-OP
//        }
//    }
//}