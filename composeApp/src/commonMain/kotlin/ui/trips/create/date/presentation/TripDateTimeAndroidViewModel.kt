package ui.trips.create.date.presentation

import core.domain.util.DateTimeFormatter
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.GetTripDateTimeUseCase
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.SetTripDateTimeUseCase
import tj.ham_safar.app.trips.create.date_time.presentation.TripDateTimeScreenEvent
import tj.ham_safar.app.trips.create.date_time.presentation.TripDateTimeViewModel

class TripDateTimeAndroidViewModel(
    setTripDateTimeUseCase: SetTripDateTimeUseCase,
    getTripDateTimeUseCase: GetTripDateTimeUseCase,
    dateTimeFormatter: DateTimeFormatter
) : ViewModel() {

    private val viewModel = TripDateTimeViewModel(
        setTripDateTimeUseCase = setTripDateTimeUseCase,
        getTripDateTimeUseCase = getTripDateTimeUseCase,
        formatter = dateTimeFormatter,
        coroutineScope = viewModelScope
    )
    val state = viewModel.state

    fun onEvent(event: TripDateTimeScreenEvent) = viewModel.onEvent(event)
}