package ui.passengers.details.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.domain.util.stringResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.core.presentation.painterResource
import passengers.detail.presentation.DetailedPassengerScreenEvent
import passengers.detail.presentation.DetailedPassengerScreenState
import tj.ham_safar.app.android.core.presentation.components.Loader
import tj.ham_safar.app.android.core.presentation.components.TopBar
import ui.passengers.details.presentation.components.DriverRequest
import tj.ham_safar.app.android.theme.*
import tj.ham_safar.app.android.trips.components.LabelRating
import tj.ham_safar.app.android.trips.components.LabelText
import tj.ham_safar.app.android.trips.components.TripDestinationPath
import ui.core.presentation.components.ErrorView
import ui.core.presentation.components.MainButton
import ui.core.presentation.getImagePainterOrPlaceHolder
import ui.theme.labelText
import ui.theme.propertyText
import ui.trips.detailed_trip.presentation.components.ProfileRatingItem

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DetailedPassengerScreen(
    state: DetailedPassengerScreenState,
    onEvent: (DetailedPassengerScreenEvent) -> Unit,
) {
    state.passenger?.let { passenger ->
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopBar(
                    text = stringResource(
                        id = "trip_id",
                        listOf(passenger.id)
                    )
                ) {
                    onEvent(DetailedPassengerScreenEvent.OnBackClick)
                }
            }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(top = 8.dp)
                    .padding(bottom = 82.dp)
                    .padding(horizontal = 21.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                                .drawBehind {
                                    TripDestinationPath()
                                }
                        ) {
                            Row(verticalAlignment = Alignment.Top) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource("ic_location.xml"),
                                    tint = Yellow,
                                    contentDescription = stringResource("location_from_icon")
                                )
                                Column {
                                    Text(
                                        text = stringResource("departure_point"),
                                        style = labelText,
                                    )
                                    Text(
                                        text = "${passenger.cityFrom.name}, ${passenger.addressFrom}",
                                        style = propertyText,
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(22.dp))
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.End
                            ) {
                                Row(verticalAlignment = Alignment.Bottom) {
                                    Column {
                                        Text(
                                            text = stringResource("place_of_arrival"),
                                            style = labelText
                                        )
                                        Text(
                                            text = "${passenger.cityTo.name}, ${passenger.addressTo}",
                                            style = propertyText
                                        )
                                    }
                                    Icon(
                                        modifier = Modifier.size(24.dp),
                                        painter = painterResource("ic_location.xml"),
                                        tint = Color.Black,
                                        contentDescription = stringResource("location_to_icon")
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))

                        LabelText(
                            label = stringResource("departure_date"),
                            text = passenger.date
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))

                        LabelText(
                            label = stringResource("departure_time"),
                            text = passenger.time
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))

                        LabelRating(
                            label = stringResource(id = "rating"),
                            rating = passenger.rating
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = stringResource("request_from"),
                            style = labelText
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                        ProfileRatingItem(
                            name = passenger.user.getFullName(),
                            rating = passenger.rating,
                            profilePainter = getImagePainterOrPlaceHolder(
                                photo = passenger.user.photo,
                                placeholderResId = "ic_user.xml"
                            )
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.size(12.dp))

                        Text(
                            text = stringResource(id = "comment"),
                            style = labelText
                        )
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .padding(top = 3.dp)
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.medium)
                                .background(PrimaryGray)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "Comment should be here",
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }

                    items(passenger.requests) { user ->
                        DriverRequest(user = user)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
//            TODO: check if i need this logic (Maybe include it later?)
//            MainButton(
//                labelRes = "write_message,
//                modifier = Modifier.fillMaxWidth(),
//                backgroundColor = Color.White
//            ) {
//                onEvent(DetailedPassengerScreenState.WriteMessage)
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))

            MainButton(
                labelRes = "book",
                modifier = Modifier.fillMaxWidth()
            ) {
                onEvent(DetailedPassengerScreenEvent.SendRequest)
            }
        }
    }

    AnimatedVisibility(visible = state.isLoading) {
        Loader()
    }

    state.error?.let { error ->
        ErrorView(
            error = error,
            onRetry = {
                state.passenger?.id?.let {
                    onEvent(DetailedPassengerScreenEvent.LoadPassenger(it))
                }
            }
        )
    }
}

//@Preview
//@Composable
//private fun DetailsPassengerScreenPreview() {
//    HamSafarTheme {
//        DetailedPassengerScreen(
//            state = DetailedPassengerScreenState(
//                passenger = Passenger(
//                    id = 0,
//                    addressFrom = "автовокзал",
//                    addressTo = "водоносос",
//                    cityFrom = City(
//                        id = 0,
//                        name = "Худжанд"
//                    ),
//                    cityTo = City(
//                        id = 0,
//                        name = "Душанбе"
//                    ),
//                    date = "6 янв. 2023",
//                    time = "12:00",
//                    rating = 4.2,
//                    count = 1,
//                    priceFrom = 120,
//                    priceTo = 170,
//                    driverId = -1,
//                    requests = emptyList(),
//                    user = User(
//                        id = 1,
//                        phone = "",
//                        name = "Ихтиёр",
//                        rating = 4.2,
//                        photo = null,
//                        surname = "Саломов",
//                        patronymic = "",
//                        dateOfBirth = "12/12/12",
//                        isDriver = false,
//                        transport = null,
//                        licenceNumber = null,
//                        licenceExpiration = null,
//                        passportNumber = null
//                    )
//                )
//            ),
//            onEvent = {}
//        )
//    }
//}