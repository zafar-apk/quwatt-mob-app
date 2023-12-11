package ui.passengers.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import core.domain.util.stringResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.core.presentation.painterResource
import tj.ham_safar.app.android.theme.*
import ui.trips.components.AddressRow
import tj.ham_safar.app.android.trips.components.DashLine
import ui.trips.components.RatingBar

@Composable
fun PassengerItem(
    fromAddress: String,
    fromCity: String,
    tripTime: String,
    tripDate: String,
    toCity: String,
    toAddress: String,
    minPrice: Int,
    maxPrice: Int,
    passengersCount: Int,
    rating: Double,
    avatar: String?,
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(modifier = modifier.clickable { onClick() }) {
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
                    text = "${fromCity}, $fromAddress"
                )
                Row(
                    modifier = Modifier.height(IntrinsicSize.Max),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DashLine(modifier = Modifier.padding(start = 19.5.dp))
                    Spacer(modifier = Modifier.size(12.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        TripDateAndTime(
                            date = tripDate,
                            time = tripTime
                        )
                        RatingBar(rating = rating)
                        PriceRange(
                            minPrice = minPrice,
                            maxPrice = maxPrice
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
                PassengerCount(passengersCount)
                Spacer(modifier = Modifier.weight(1F))
                PassengerInfo(avatar, name)
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun PassengerCount(passengersCount: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = passengersCount.toString(),
            style = MaterialTheme.typography.body2.copy(color = Gray)
        )
        Spacer(modifier = Modifier.size(3.dp))
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource("ic_people.xml"),
            contentDescription = stringResource(id = "passengers")
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun PassengerInfo(
    avatar: String?,
    name: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
                .background(LightGray),
            contentDescription = stringResource(id = "user_photo"),
            painter = if (avatar.isNullOrEmpty()) {
                painterResource("ic_user.xml")
            } else {
                painterResource("ic_user.xml")

//                rememberAsyncImagePainter(
//                    model = avatar.asRemoteImage(),
//                    placeholder = painterResource(id = R.drawable.ic_user),
//                    error = painterResource(id = R.drawable.ic_user)
//                )
            }
        )
        Spacer(modifier = Modifier.size(2.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
private fun PriceRange(
    minPrice: Int,
    maxPrice: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(
            id = "from_price_to_price",
            listOf(
                minPrice,
                maxPrice
            )
        ),
        style = LocalTextStyle.current.merge(
            MaterialTheme.typography.body2.copy(
                color = Color.White,
//                platformStyle = PlatformTextStyle(
////                    includeFontPadding = false
//                ),
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.Both
                )
            )
        ),
        modifier = modifier
            .background(
                color = Yellow,
                MaterialTheme.shapes.medium
            )
            .padding(horizontal = 5.dp, vertical = 2.dp)
    )
}

@Composable
private fun TripDateAndTime(
    date: String,
    time: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.body2.copy(color = Gray)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = time,
            style = MaterialTheme.typography.body2.copy(color = Gray)
        )
    }
}

//@Preview
//@Composable
//fun TripItemPreview() {
//    HamSafarTheme {
//        PassengerItem(
//            fromAddress = "Автовокзал",
//            fromCity = "Худжанд",
//            tripTime = "12:00",
//            tripDate = "02.02.23",
//            toCity = "Душанбе",
//            toAddress = "Водонасос",
//            minPrice = 50,
//            maxPrice = 150,
//            passengersCount = 4,
//            rating = 1.0,
//            avatar = null,
//            name = "Hannah J.",
//            onClick = {}
//        )
//    }
//}