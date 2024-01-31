package ui.stations.filter.presentation

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.presentation.DatePickerDialog
import dev.icerock.moko.resources.compose.stringResource
import tj.ham_safar.app.android.core.presentation.components.ActionButton
import tj.quwatt.quwattapp.SharedRes
import stations.filter.presentation.TripFilterScreenEvent
import stations.filter.presentation.TripFilterScreenState
import ui.core.presentation.components.BackButton
import ui.core.presentation.components.MainButton
import ui.core.presentation.components.TimePickerDialog
import ui.stations.core.mapper.toTripsFilterParcelize
import ui.stations.filter.presentation.components.FilterDatePicker
import ui.stations.filter.presentation.components.FilterTimePicker
import ui.stations.filter.presentation.components.RangePicker
import ui.stations.filter.presentation.components.TextDropDown

@Composable
fun TripFilterScreen(
    state: TripFilterScreenState,
    onEvent: (TripFilterScreenEvent) -> Unit
) {
    LaunchedEffect(state.shouldGoBackWithResult) {
        if (state.shouldGoBackWithResult) {
            val filterResult = state.toTripsFilterParcelize()
            onEvent(TripFilterScreenEvent.GoBackWithResult(filterResult))
        }
    }

    LaunchedEffect(state.resetPriceValues) {
        if (state.resetPriceValues) onEvent(
            TripFilterScreenEvent.OnPriceValuesReseted
        )
    }

    if (state.choosingState.isChoosingDate) {
        DatePickerDialog(
            onDismissRequest = {
                onEvent(TripFilterScreenEvent.DismissAllChoosing)
            },
            onDateSelected = { selectedDate ->
                onEvent(TripFilterScreenEvent.ChangeTripDate(selectedDate))
            },
            dateSeparator = "-"
        )
    }

    if (state.choosingState.isChoosingTimeDate) {
        TimePickerDialog(
            onDismissRequest = {
                onEvent(TripFilterScreenEvent.DismissAllChoosing)
            },
            onTimePicked = { selectedTime ->
                onEvent(TripFilterScreenEvent.ChangeTripTime(selectedTime))
            }
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
                    BackButton { onEvent(TripFilterScreenEvent.BackPress) }

                    Text(
                        text = stringResource(SharedRes.strings.filters),
                        style = MaterialTheme.typography.body1.copy(fontSize = 20.sp)
                    )

                    ActionButton(
                        icon = Icons.Default.Refresh,
                        onClick = { onEvent(TripFilterScreenEvent.ResetFilter) },
                        contentDescription = stringResource(SharedRes.strings.reset)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.size(36.dp))
                TextDropDown(
                    modifier = Modifier.fillMaxWidth(),
                    hint = stringResource(SharedRes.strings.auto_type),
                    selectedText = state.selectedState.selectedAutoType
                        ?: stringResource(SharedRes.strings.any),
                    isDropDownOpened = state.choosingState.isChoosingAutoType,
                    options = state.autoTypes,
                    onOpenDropDown = { onEvent(TripFilterScreenEvent.OpenAutoTypeDropDown) },
                    onSelectOption = {
                        onEvent(TripFilterScreenEvent.ChangeAutoType(it))
                    },
                    onDismiss = {
                        onEvent(TripFilterScreenEvent.DismissAllChoosing)
                    }
                )
            }

//            item {
//                Spacer(modifier = Modifier.height(12.dp))
//
//                TextDropDown(
//                    modifier = Modifier.fillMaxWidth(),
//                    hint = stringResource(SharedRes.strings.auto_model),
//                    selectedText = state.selectedState.selectedAutoModel
//                        ?: stringResource(SharedRes.strings.any),
//                    isDropDownOpened = state.choosingState.isChoosingAutoModel,
//                    options = state.autoModels,
//                    onOpenDropDown = { onEvent(TripFilterScreenEvent.OpenAutoModelDropDown) },
//                    onSelectOption = {
//                        onEvent(TripFilterScreenEvent.ChangeAutoModel(it))
//                    },
//                    onDismiss = {
//                        onEvent(TripFilterScreenEvent.DismissAllChoosing)
//                    }
//                )
//            }

            item {
                Spacer(modifier = Modifier.height(12.dp))

                RangePicker(
                    text = stringResource(SharedRes.strings.trip_fee),
                    reset = state.resetPriceValues,
                    bounds = 1F..500F,
                    value = state.selectedState.selectedFromPriceTrip..state.selectedState.selectedToPriceTrip,
                    onValueChange = {
                        onEvent(TripFilterScreenEvent.ChangePriceFrom(it.start))
                        onEvent(TripFilterScreenEvent.ChangePriceTo(it.endInclusive))
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))

                TextDropDown(
                    modifier = Modifier.fillMaxWidth(),
                    hint = stringResource(SharedRes.strings.departure_city),
                    selectedText = state.selectedState.selectedFromCity?.name
                        ?: stringResource(SharedRes.strings.any),
                    isDropDownOpened = state.choosingState.isChoosingFromCity,
                    options = state.cities.map { it.name },
                    onOpenDropDown = { onEvent(TripFilterScreenEvent.OpenFromCityDropDown) },
                    onSelectOption = { city ->
                        onEvent(
                            TripFilterScreenEvent.ChangeFromCity(
                                state.cities.first { it.name == city }
                            )
                        )
                    },
                    onDismiss = {
                        onEvent(TripFilterScreenEvent.DismissAllChoosing)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))

                TextDropDown(
                    modifier = Modifier.fillMaxWidth(),
                    hint = stringResource(SharedRes.strings.arrival_city),
                    selectedText = state.selectedState.selectedToCity?.name
                        ?: stringResource(SharedRes.strings.any),
                    isDropDownOpened = state.choosingState.isChoosingToCity,
                    options = state.cities.map { it.name },
                    onOpenDropDown = { onEvent(TripFilterScreenEvent.OpenToCityDropDown) },
                    onSelectOption = { city ->
                        onEvent(TripFilterScreenEvent.ChangeToCity(state.cities.first { it.name == city }))
                    },
                    onDismiss = {
                        onEvent(TripFilterScreenEvent.DismissAllChoosing)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))

                FilterDatePicker(
                    hint = stringResource(SharedRes.strings.trip_date),
                    selectedText = state.selectedState.selectedTripDate
                        ?: stringResource(SharedRes.strings.any),
                    onShowDatePicker = {
                        onEvent(TripFilterScreenEvent.OpenTripDateCalendar)
                    },
                    onDateSelected = {
                        onEvent(TripFilterScreenEvent.ChangeTripDate(it))
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
                        onEvent(TripFilterScreenEvent.OpenTripTimeDialog)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))

                TextDropDown(
                    modifier = Modifier.fillMaxWidth(),
                    hint = stringResource(SharedRes.strings.rating),
                    selectedText = if ((state.selectedState.selectedTripRating ?: -1) == -1) {
                        stringResource(SharedRes.strings.any)
                    } else {
                        state.selectedState.selectedTripRating.toString()
                    },
                    isDropDownOpened = state.choosingState.isChoosingRating,
                    options = (1..5).map { it.toString() },
                    onOpenDropDown = { onEvent(TripFilterScreenEvent.OpenTripRatingDropDown) },
                    onSelectOption = {
                        onEvent(TripFilterScreenEvent.ChangeTripRating(it.toInt()))
                    },
                    onDismiss = {
                        onEvent(TripFilterScreenEvent.DismissAllChoosing)
                    }
                )
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
                onEvent(TripFilterScreenEvent.Submit)
            }
            Spacer(modifier = Modifier.size(32.dp))
        }
    }
}