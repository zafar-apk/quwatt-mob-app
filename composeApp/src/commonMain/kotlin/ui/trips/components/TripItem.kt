package ui.trips.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import tj.ham_safar.app.android.trips.components.DashLine
import tj.quwatt.quwattapp.SharedRes
import trips.all.domain.models.Trip
import ui.theme.TextBlack
import ui.theme.Yellow

@Composable
fun TripItem(
    trip: Trip,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(modifier = modifier.clickable(onClick = onClick)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(vertical = 8.dp)
        ) {
            Column {
                AddressRow(
                    modifier = Modifier.padding(start = 11.dp),
                    iconColor = Yellow,
                    text = "${trip.cityFrom}, ${trip.addressFrom}"
                )
                Row(
                    modifier = Modifier.height(IntrinsicSize.Max),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DashLine(modifier = Modifier.padding(start = 19.5.dp))
                    Image(
                        painter = painterResource(SharedRes.images.car_placeholder),
                        contentDescription = stringResource(SharedRes.strings.car_placeholder),
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
                            text = trip.time,
                            style = LocalTextStyle.current.merge(
                                MaterialTheme.typography.body2.copy(
//                                    platformStyle = PlatformTextStyle(includeFontPadding = false),
                                    lineHeightStyle = LineHeightStyle(
                                        alignment = LineHeightStyle.Alignment.Center,
                                        trim = LineHeightStyle.Trim.Both
                                    )
                                )
                            )
                        )
//                        RatingBar(rating = trip.rating)
//                        Text(
//                            text = stringResource(
//                                id = "from_price_to_price",
//                                listOf(
//                                    trip.minPrice,
//                                    trip.maxPrice
//                                )
//                            ),
//                            style = LocalTextStyle.current.merge(
//                                MaterialTheme.typography.body2.copy(
//                                    color = Color.White,
////                                    platformStyle = PlatformTextStyle(includeFontPadding = false),
//                                    lineHeightStyle = LineHeightStyle(
//                                        alignment = LineHeightStyle.Alignment.Center,
//                                        trim = LineHeightStyle.Trim.Both
//                                    )
//                                )
//                            ),
//                            modifier = Modifier
//                                .background(
//                                    color = Yellow,
//                                    MaterialTheme.shapes.medium
//                                )
//                                .padding(horizontal = 5.dp, vertical = 2.dp)
//                        )
                    }
                }
                AddressRow(
                    modifier = Modifier.padding(start = 12.dp),
                    iconColor = TextBlack,
                    text = with(trip) { "$cityTo, $addressTo" }
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
                    text = trip.date,
                    style = MaterialTheme.typography.body2
                )
//                with(trip) {
//                    PassengersCount(
//                        progress = (seats.size - availableSeatsCount).toFloat() / seats.size.toFloat(),
//                        text = "${seats.size - availableSeatsCount}/${seats.size}"
//                    )
//                }

//                Text(
//                    text = resources.getQuantityString(
//                        R.plurals.seats_plurals, availableSeats, availableSeats
//                    )
//                )
            }
        }
    }
}