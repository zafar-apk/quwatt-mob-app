package ui.stations.detailed_booking_trip.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.domain.util.stringResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import stations.all.domain.models.BookedTrip
import tj.ham_safar.app.android.core.presentation.components.TopBar
import tj.ham_safar.app.android.trips.components.LabelRating
import tj.ham_safar.app.android.trips.components.LabelText
import tj.ham_safar.app.android.trips.components.PassengersCount
import tj.ham_safar.app.android.trips.components.TripDestinationPath
import tj.ham_safar.app.trips.detailed_booking_trip.presentation.DetailedBookedTripScreenEvent
import tj.ham_safar.app.trips.detailed_booking_trip.presentation.DetailedBookedTripScreenState
import ui.core.presentation.components.ErrorView
import ui.core.presentation.components.Loader
import ui.core.presentation.components.MainButton
import ui.core.presentation.dialogs.RequestConfirmationDialog
import ui.core.presentation.getImagePainterOrPlaceHolder
import ui.core.presentation.painterResource
import ui.theme.Primary
import ui.theme.labelText
import ui.theme.propertyText

private const val MIN_PROGRESS = 0.1F

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DetailedBookedTripScreen(
    state: DetailedBookedTripScreenState,
    onEvent: (DetailedBookedTripScreenEvent) -> Unit,
) {

//    val context = LocalContext.current
    LaunchedEffect(state.toastError) {
        if (state.toastError != null) {
//            Toast.makeText(context, state.toastError, Toast.LENGTH_LONG).show()
            onEvent(DetailedBookedTripScreenEvent.ResetState)
        }
    }

    LaunchedEffect(state.shouldGoBack) {
        if (state.shouldGoBack) {
            onEvent(DetailedBookedTripScreenEvent.GoBack)
            onEvent(DetailedBookedTripScreenEvent.ResetState)
        }
    }

    state.trip?.let { trip ->
        if (state.shouldRequestBookingCancellationConfirmation)
            RequestConfirmationDialog(
                text = stringResource(id = "are_you_sure_you_want_to_cancel_trip"),
                onCancel = { onEvent(DetailedBookedTripScreenEvent.ResetState) },
                onConfirm = { onEvent(DetailedBookedTripScreenEvent.CancelBookedTrip) }
            )

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopBar(text = stringResource(id = "trip_id", listOf(trip.id))) {
                    onEvent(DetailedBookedTripScreenEvent.OnBackClick)
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
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop,
                            painter = getImagePainterOrPlaceHolder(
//                                photo = trip.driver.transport?.photo,
                                photo = "",
                                placeholderResId = "car_placeholder@1x.png"
                            ),
                            contentDescription = stringResource(id = "transport_photo")
                        )
                    }

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
                                    tint = Primary,
                                    contentDescription = stringResource("location_from_icon")
                                )
                                Column {
                                    Text(
                                        text = stringResource("departure_point"),
                                        style = labelText,
                                    )
                                    Text(
                                        text = "${trip.cityFrom.name}, ${trip.addressFrom}",
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
                                            text = "${trip.cityTo.name}, ${trip.addressTo}",
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
                            text = trip.date
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))

                        LabelText(
                            label = stringResource("departure_time"),
                            text = trip.time
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))

                        LabelRating(
                            label = stringResource(id = "rating"),
                            rating = trip.rating
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))

//                        LabelText(
//                            label = stringResource("automobile"),
//                            text = "${trip.driver.transport?.type}, ${trip.driver.transport?.brand}"
//                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TripExtraOptions(trip)
                            TripPassengersProgress(trip)
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = stringResource("driver"), style = labelText)
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))
//                        ProfileRatingItem(
//                            name = "${trip.driver.name} ${trip.driver.surname}",
//                            rating = trip.driver.rating,
//                            profilePainter = getImagePainterOrPlaceHolder(
//                                photo = trip.driver.photo,
//                                placeholderResId = "ic_user.xml"
//                            )
//                        )
                    }

                    item {
                        if (trip.passengers.isNotEmpty()) {
                            Text(
                                modifier = Modifier.padding(vertical = 12.dp),
                                text = stringResource(id = "passengers"),
                                style = labelText
                            )
                        }
                    }

                    items(trip.passengers) { passenger ->
//                        ProfileRatingItem(
//                            name = passenger.getFullName(),
//                            rating = passenger.rating,
//                            profilePainter = getImagePainterOrPlaceHolder(
//                                photo = passenger.photo,
//                                placeholderResId = "ic_user.xml"
//                            )
//                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
            state.error?.let { error ->
                ErrorView(error = error)
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
                .padding(21.dp)
        ) {
//            TODO: check if i need this logic (Maybe include it later?)
//            MainButton(
//                labelRes = "write_message,
//                modifier = Modifier.fillMaxWidth(),
//                backgroundColor = Color.White
//            ) {
//                onEvent(DetailedBookedTripScreenEvent.WriteMessage)
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))

            if (state.trip != null) MainButton(
                labelRes = "cancellation",
                modifier = Modifier.fillMaxWidth()
            ) {
                onEvent(DetailedBookedTripScreenEvent.RequestBookedTripCancellationConfirmation)
            }
        }
    }


    AnimatedVisibility(visible = state.isLoading) {
        Loader()
    }
}

@Composable
private fun TripPassengersProgress(trip: BookedTrip) {
//    val resources = LocalContext.current.resources

    Column(horizontalAlignment = Alignment.End) {
        PassengersCount(
            progress = calculateTripPassengersProgress(trip),
            text = getTripPassengersCountText(trip)
        )

        Text(
            text = "TODO:))"
//            resources.getQuantityString(
//                R.plurals.remained_seats_plurals,
//                trip.availableSeatsCount,
//                trip.availableSeatsCount
//            )
            ,
            style = labelText,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun getTripPassengersCountText(trip: BookedTrip) = with(trip) {
    "${(seats.size) + (passengers.size) - availableSeatsCount}/${((seats.size) + (passengers.size))}"
}

@Composable
private fun calculateTripPassengersProgress(trip: BookedTrip) = with(trip) {
    (((seats.size) + (passengers.size) - availableSeatsCount).toFloat() / ((seats.size) + (passengers.size)).toFloat())
        .coerceAtLeast(MIN_PROGRESS)
}

@Composable
private fun TripExtraOptions(trip: BookedTrip) {
    Column {
        Text(
            text = stringResource("additional_options"),
            style = labelText
        )
        Spacer(modifier = Modifier.height(12.dp))
        // u gotta work here))
//        FlowRow(
//            mainAxisAlignment = MainAxisAlignment.Start,
//            mainAxisSize = SizeMode.Wrap,
//            crossAxisSpacing = 12.dp,
//            mainAxisSpacing = 8.dp
//        ) {
//            val listOfExtraOptions = mutableListOf<String>()
//            if (trip.driver.transport?.hasConditioner == true) {
//                listOfExtraOptions.add(stringResource("conditioner))
//            }
//            listOfExtraOptions.forEach {
//                Box(
//                    modifier = Modifier
//                        .clip(RoundedCornerShape(6.dp))
//                        .background(Yellow),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(modifier = Modifier.padding(4.dp), text = it)
//                }
//            }
//        }
    }
}

//@Preview
//@Composable
//fun DetailsScreenPreview() {
//    HamSafarTheme {
//        DetailedBookedTripScreen(
//            state = DetailedBookedTripScreenState(
//                trip = BookedTrip(
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
//                    driver = User(
//                        id = 0,
//                        photo = null,
//                        phone = "",
//                        name = "Мурод",
//                        surname = "Аловатов",
//                        patronymic = "",
//                        dateOfBirth = "",
//                        isDriver = true,
//                        transport = Transport(
//                            id = 2,
//                            photo = null,
//                            type = TransportType.SEDAN,
//                            brand = TransportBrand.MERCEDES,
//                            colors = TransportColors.BROWN,
//                            model = "abv",
//                            capacity = 4,
//                            dateOfIssue = "2008",
//                            hasConditioner = true
//                        ),
//                        rating = 4.0,
//                        licenceExpiration = null,
//                        licenceNumber = null,
//                        passportNumber = null
//                    ),
//                    status = "AVAILABLE",
//                    seats = listOf(),
//                    passengers = listOf(),
//                    price = 200,
//                    availableSeatsCount = 4,
//                    rating = 5.0
//                )
//            ),
//            onEvent = {}
//        )
//    }
//}