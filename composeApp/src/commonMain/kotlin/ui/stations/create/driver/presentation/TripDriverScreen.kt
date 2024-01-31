package ui.stations.create.driver.presentation

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.domain.util.stringResource
import tj.ham_safar.app.android.core.presentation.Routes
import ui.core.presentation.components.BackButton
import ui.core.presentation.components.Loader
import ui.core.presentation.components.MainButton
import ui.stations.components.CounterButton
import ui.stations.components.CounterIcon
import tj.ham_safar.app.android.trips.components.DesiredPriceTextField
import tj.ham_safar.app.trips.create.driver.presentation.TripDriverScreenEvent
import tj.ham_safar.app.trips.create.driver.presentation.TripDriverScreenState

@Composable
fun TripDriverScreen(
    state: TripDriverScreenState,
    onEvent: (TripDriverScreenEvent) -> Unit
) {

//    val context = LocalContext.current
    LaunchedEffect(state.shouldGoNext) {
        if (state.shouldGoNext) {
            onEvent(TripDriverScreenEvent.GoNext)
            onEvent(TripDriverScreenEvent.ResetState)
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let { error ->
//            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            onEvent(TripDriverScreenEvent.ResetState)
        }
    }

    LaunchedEffect(state.shouldGoToTripLocation) {
        if (state.shouldGoToTripLocation) {
//            Toast.makeText(context, Strings.enterData, Toast.LENGTH_LONG).show()
            onEvent(TripDriverScreenEvent.GoBack(Routes.CREATE_TRIP_LOCATION))
            onEvent(TripDriverScreenEvent.ResetState)
        }
    }

    LaunchedEffect(state.shouldGoToTripDateTime) {
        if (state.shouldGoToTripDateTime) {
//            Toast.makeText(context, Strings.enterData, Toast.LENGTH_LONG).show()
            onEvent(TripDriverScreenEvent.GoBack(Routes.CREATE_TRIP_DATE_TIME))
            onEvent(TripDriverScreenEvent.ResetState)
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
            onEvent(TripDriverScreenEvent.GoBack())
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
                onEvent(TripDriverScreenEvent.OnCountMinus)
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
                onEvent(TripDriverScreenEvent.OnCountPlus)
            }
        }

        Text(
            modifier = Modifier.padding(top = 36.dp, bottom = 16.dp),
            style = MaterialTheme.typography.h2,
            text = stringResource(id = "desired_price_per_passenger"),
            textAlign = TextAlign.Center
        )

        DesiredPriceTextField(
            price = state.price,
            onPriceChanged = { onEvent(TripDriverScreenEvent.OnPriceChanged(it)) },
            imeAction = ImeAction.Done,
            modifier = Modifier
                .fillMaxWidth(.6f)
                .height(62.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        MainButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 26.dp),
            labelRes = "create_trip",
        ) { onEvent(TripDriverScreenEvent.CreateTrip) }
    }
    AnimatedVisibility(visible = state.isRequestSent) {
        Loader()
    }
}