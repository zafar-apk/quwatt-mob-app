package ui.passengers.filter.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.domain.util.stringResource
import passengers.filter.presentation.PassengerFilterScreenEvent
import passengers.filter.presentation.PassengerFilterScreenState
import tj.ham_safar.app.android.core.presentation.components.ActionButton
import tj.yakroh.yakrohapp.SharedRes
import ui.core.presentation.components.BackButton
import ui.core.presentation.components.MainButton
import ui.passengers.core.shared_trip_filter.PassengerTripsFilterParcelize
import ui.passengers.core.shared_trip_filter.toPassengerTripsFilter
import ui.passengers.core.shared_trip_filter.toTripsFilterParcelize
import ui.trips.filter.presentation.components.FilterDatePicker
import ui.trips.filter.presentation.components.FilterTimePicker
import ui.trips.filter.presentation.components.RangePicker
import ui.trips.filter.presentation.components.TextDropDown

@Composable
fun PassengerFilterScreen(
    returnedFilterState: State<PassengerTripsFilterParcelize?>?,
    state: PassengerFilterScreenState,
    onEvent: (PassengerFilterScreenEvent) -> Unit
) {
//    val resources = LocalContext.current.resources

    LaunchedEffect(state.shouldGoBackWithResult) {
        if (state.shouldGoBackWithResult) {
            val filterResult = state.toTripsFilterParcelize()
            onEvent(PassengerFilterScreenEvent.GoBackWithResult(filterResult))
        }
    }

    LaunchedEffect(state.resetPriceValues) {
        if (state.resetPriceValues) onEvent(
            PassengerFilterScreenEvent.OnPriceValuesReseted
        )
    }


    LaunchedEffect(returnedFilterState) {
        val filterParcelize = returnedFilterState?.value
        if (filterParcelize != null) onEvent(
            PassengerFilterScreenEvent.PassengersFilterDataReturned(
                filterParcelize.toPassengerTripsFilter()
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
            .padding(horizontal = 21.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BackButton { onEvent(PassengerFilterScreenEvent.BackPress) }

                    Text(
                        text = stringResource(SharedRes.strings.filters),
                        style = MaterialTheme.typography.body1.copy(fontSize = 20.sp)
                    )

                    ActionButton(
                        icon = Icons.Default.Refresh,
                        onClick = { onEvent(PassengerFilterScreenEvent.ResetFilter) },
                        contentDescription = stringResource(id = SharedRes.strings.rating)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))

                RangePicker(
                    text = stringResource(SharedRes.strings.trip_fee),
                    reset = state.resetPriceValues,
                    lowerBound = state.selectedState.selectedFromPriceTrip,
                    upperBound = state.selectedState.selectedToPriceTrip,
                    onLowerBoundChange = { onEvent(PassengerFilterScreenEvent.ChangePriceFrom(it)) },
                    onUpperBoundChange = { onEvent(PassengerFilterScreenEvent.ChangePriceTo(it)) })
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))

                TextDropDown(modifier = Modifier.fillMaxWidth(),
                    hint = stringResource(SharedRes.strings.departure_city),
                    selectedText = state.selectedState.selectedFromCity?.name
                        ?: stringResource(SharedRes.strings.any),
                    isDropDownOpened = state.choosingState.isChoosingFromCity,
                    options = state.cities.map { it.name },
                    onOpenDropDown = { onEvent(PassengerFilterScreenEvent.OpenFromCityDropDown) },
                    onSelectOption = { city ->
                        onEvent(PassengerFilterScreenEvent.ChangeFromCity(state.cities.first { it.name == city }))
                    },
                    onDismiss = {
                        onEvent(PassengerFilterScreenEvent.DismissAllChoosing)
                    })
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))

                TextDropDown(modifier = Modifier.fillMaxWidth(),
                    hint = stringResource(SharedRes.strings.arrival_city),
                    selectedText = state.selectedState.selectedToCity?.name
                        ?: stringResource(SharedRes.strings.any),
                    isDropDownOpened = state.choosingState.isChoosingToCity,
                    options = state.cities.map { it.name },
                    onOpenDropDown = { onEvent(PassengerFilterScreenEvent.OpenToCityDropDown) },
                    onSelectOption = { city ->
                        onEvent(PassengerFilterScreenEvent.ChangeToCity(state.cities.first { it.name == city }))
                    },
                    onDismiss = {
                        onEvent(PassengerFilterScreenEvent.DismissAllChoosing)
                    })
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))

                FilterDatePicker(hint = stringResource(SharedRes.strings.trip_date),
                    selectedText = state.selectedState.selectedTripDate
                        ?: stringResource(SharedRes.strings.any),
                    onDateSelected = {
//                        onEvent(PassengerFilterScreenEvent.ChangeTripDate(it.let(Long::toDateDDMMYYYY)))
                    },
                    onShowDatePicker = {

                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))

                FilterTimePicker(
                    hint = stringResource(SharedRes.strings.trip_time),
                    selectedText = state.selectedState.selectedTripTime
                        ?: stringResource(SharedRes.strings.any),
                    onOpenTimePicker = {
                        onEvent(PassengerFilterScreenEvent.OpenTripTimeDialog)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))

                TextDropDown(modifier = Modifier.fillMaxWidth(),
                    hint = stringResource(SharedRes.strings.rating),
                    selectedText = if ((state.selectedState.selectedTripRating ?: -1) == -1) {
                        stringResource(SharedRes.strings.any)
                    } else {
                        state.selectedState.selectedTripRating.toString()
                    },
                    isDropDownOpened = state.choosingState.isChoosingRating,
                    options = (1..5).map { it.toString() },
                    onOpenDropDown = { onEvent(PassengerFilterScreenEvent.OpenTripRatingDropDown) },
                    onSelectOption = {
                        onEvent(PassengerFilterScreenEvent.ChangeTripRating(it.toInt()))
                    },
                    onDismiss = {
                        onEvent(PassengerFilterScreenEvent.DismissAllChoosing)
                    })
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(
                    SharedRes.plurals.available_trips_plurals,
                    state.availableTrips,
                    state.availableTrips
                ),
                style = MaterialTheme.typography.h3
            )

            Spacer(modifier = Modifier.size(14.dp))

            MainButton(
                modifier = Modifier.fillMaxWidth(),
                labelResource = SharedRes.strings.apply
            ) {
                onEvent(PassengerFilterScreenEvent.Submit)
            }
            Spacer(modifier = Modifier.size(32.dp))
        }
    }
}