package ui.trips.create.passengers.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.domain.util.stringResource
import tj.ham_safar.app.android.core.presentation.Routes
import ui.core.presentation.components.BackButton
import tj.ham_safar.app.android.core.presentation.components.Loader
import ui.core.presentation.components.MainButton
import ui.trips.components.CounterButton
import ui.trips.components.CounterIcon
import tj.ham_safar.app.android.trips.components.DesiredPriceTextField
import tj.ham_safar.app.trips.create.passengers.presentation.TripPassengersScreenEvent
import tj.ham_safar.app.trips.create.passengers.presentation.TripPassengersScreenState

@Composable
fun TripPassengerScreen(
    state: TripPassengersScreenState,
    onEvent: (TripPassengersScreenEvent) -> Unit
) {

//    val context = LocalContext.current
    LaunchedEffect(state.shouldGoNext) {
        if (state.shouldGoNext) {
            onEvent(TripPassengersScreenEvent.GoNext)
            onEvent(TripPassengersScreenEvent.ResetState)
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let { error ->
//            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            onEvent(TripPassengersScreenEvent.ResetState)
        }
    }

    LaunchedEffect(state.shouldGoToTripLocation) {
        if (state.shouldGoToTripLocation) {
//            Toast.makeText(context, Strings.enterData, Toast.LENGTH_LONG).show()
            onEvent(TripPassengersScreenEvent.GoBack(Routes.CREATE_TRIP_LOCATION))
            onEvent(TripPassengersScreenEvent.ResetState)
        }
    }

    LaunchedEffect(state.shouldGoToTripDateTime) {
        if (state.shouldGoToTripDateTime) {
//            Toast.makeText(context, Strings.enterData, Toast.LENGTH_LONG).show()
            onEvent(TripPassengersScreenEvent.GoBack(Routes.CREATE_TRIP_DATE_TIME))
            onEvent(TripPassengersScreenEvent.ResetState)
        }
    }

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
            onEvent(TripPassengersScreenEvent.GoBack())
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
                counterIcon = CounterIcon.NEGATIVE
            ) {
                onEvent(TripPassengersScreenEvent.OnCountMinus)
            }

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.subtitle1,
                fontSize = 48.sp,
                text = state.count.toString()
            )

            CounterButton(
                Modifier.size(46.dp),
                counterIcon = CounterIcon.POSITIVE
            ) {
                onEvent(TripPassengersScreenEvent.OnCountPlus)
            }
        }

        Text(
            modifier = Modifier.padding(top = 36.dp),
            style = MaterialTheme.typography.h2,
            text = stringResource(id = "desired_price_per_passenger"),
            textAlign = TextAlign.Center
        )

        Row(modifier = Modifier.padding(top = 16.dp)) {

            DesiredPriceTextField(
                prefix = stringResource(id = "from"),
                currency = stringResource(id = "currency_somoni_pattern_c_"),
                price = state.priceFrom,
                onPriceChanged = { onEvent(TripPassengersScreenEvent.OnPriceFromChanged(it)) },
                modifier = Modifier
                    .width(92.dp)
                    .height(56.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            DesiredPriceTextField(
                prefix = stringResource(id = "to"),
                currency = stringResource(id = "currency_somoni_pattern_c_"),
                price = state.priceTo,
                imeAction = ImeAction.Done,
                onPriceChanged = { onEvent(TripPassengersScreenEvent.OnPriceToChanged(it)) },
                modifier = Modifier
                    .width(92.dp)
                    .height(56.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        MainButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 26.dp),
            labelRes = "leave_a_request",
        ) { onEvent(TripPassengersScreenEvent.LeaveRequest) }

    }
    AnimatedVisibility(visible = state.isRequestSent) {
        Loader()
    }
}