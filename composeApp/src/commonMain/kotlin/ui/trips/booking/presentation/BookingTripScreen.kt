package ui.trips.booking.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.domain.util.stringResource
import ui.core.presentation.components.BackButton
import ui.core.presentation.components.Loader
import ui.core.presentation.components.MainButton
import ui.core.presentation.dialogs.RequestConfirmationDialog
import ui.trips.components.CounterButton
import ui.trips.components.CounterIcon
import trips.booking.presentation.BookingTripScreenEvent
import trips.booking.presentation.BookingTripScreenState

@Composable
fun BookingTripScreen(
    state: BookingTripScreenState,
    onEvent: (BookingTripScreenEvent) -> Unit
) {

//    val context = LocalContext.current
    LaunchedEffect(state.error) {
        state.error?.let { error ->
//            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            onEvent(BookingTripScreenEvent.ResetState)
        }
    }

    LaunchedEffect(state.shouldGoBack) {
        if (state.shouldGoBack){
            onEvent(BookingTripScreenEvent.GoBack)
            onEvent(BookingTripScreenEvent.ResetState)
        }
    }

    if (state.shouldRequestBookingConfirmation)
        RequestConfirmationDialog(
            text = stringResource(id = "are_you_sure_you_want_booking_trip"),
            onCancel = { onEvent(BookingTripScreenEvent.ResetState) },
            onConfirm = { onEvent(BookingTripScreenEvent.BookATrip) }
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 22.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BackButton(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp)
        ) {
            onEvent(BookingTripScreenEvent.GoBack)
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.padding(top = 36.dp),
            style = MaterialTheme.typography.h2,
            text = stringResource(id = "select_the_number_of_passengers"),
            textAlign = TextAlign.Center
        )

        Row(modifier = Modifier.padding(top = 22.dp)) {
            CounterButton(
                Modifier.size(46.dp),
                counterIcon = CounterIcon.NEGATIVE,
                enabled = state.isNegativeCounterAvailable

            ) {
                onEvent(BookingTripScreenEvent.OnCountMinus)
            }

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.subtitle1,
                fontSize = 48.sp,
                text = state.count.toString()
            )

            CounterButton(
                Modifier.size(46.dp),
                counterIcon = CounterIcon.POSITIVE,
                enabled = state.isPositiveCounterAvailable
            ) {
                onEvent(BookingTripScreenEvent.OnCountPlus)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        if (state.tripData != null) MainButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 26.dp),
            labelRes = "book",
        ) { onEvent(BookingTripScreenEvent.RequestBookingConfirmation) }

    }
    AnimatedVisibility(visible = state.isLoading) {
        Loader()
    }
}