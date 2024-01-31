package tj.ham_safar.app.trips.create.date_time.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import core.domain.Strings
import core.domain.util.DateTimeFormatter
import stations.core.create_trip.domain.models.TripDateTime
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.GetTripDateTimeUseCase
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.SetTripDateTimeUseCase

class TripDateTimeViewModel(
    private val setTripDateTimeUseCase: SetTripDateTimeUseCase,
    private val getTripDateTimeUseCase: GetTripDateTimeUseCase,
    private val formatter: DateTimeFormatter,
    private val coroutineScope: CoroutineScope? = null
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(TripDateTimeScreenState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TripDateTimeScreenState()
    )


    init {
        tryToGetAlreadySetDateTime()
    }


    fun onEvent(event: TripDateTimeScreenEvent) = when (event) {
        TripDateTimeScreenEvent.GoNext -> onGoNext()

        TripDateTimeScreenEvent.ResetState -> _state.update {
            it.copy(shouldGoNext = false, error = null)
        }

        is TripDateTimeScreenEvent.OnTimeChanged -> _state.update { screenState ->
            val isTimeValid = formatter.validateTime(event.time)
            screenState.copy(
                time = event.time,
                isTimeValid = isTimeValid,
                title = getTitleWithTime(event.time, isTimeValid)
            )
        }

        is TripDateTimeScreenEvent.OnDateChanged -> _state.update { screenState ->

            val isDateValid = formatter.validateDate(event.date)
            screenState.copy(
                date = event.date,
                isDateValid = isDateValid,
                title = getTitleWithDate(event.date, isDateValid)
            )
        }

        else -> Unit
    }

    private fun getTitleWithTime(time: String, isTimeValid: Boolean): String? {
        val currentState = state.value
        if (isTimeValid.not() || currentState.isDateValid.not()) return null
        return "${formatter.formatDate(currentState.date!!)}\n${formatter.formatTime(time)}"
    }


    private fun getTitleWithDate(date: String, isDateValid: Boolean): String? {
        val currentState = state.value
        if (isDateValid.not() || currentState.isTimeValid.not()) return null
        return "${formatter.formatDate(date)}\n${formatter.formatTime(currentState.time!!)}"
    }


    private fun onGoNext() = _state.update { screenState ->
        val isDateTimeNullOrBlank =
            screenState.date.isNullOrBlank() || screenState.time.isNullOrBlank()
        when {
            isDateTimeNullOrBlank -> screenState.copy(error = Strings.enterData)
            formatter.isDateTimeInvalid(screenState.time, screenState.date) ->
                screenState.copy(error = Strings.invalidDateTimeFormat)
            else -> {
                setTripDateTimeUseCase(
                    TripDateTime(
                        formatter.formatDate(screenState.date)!!,
                        formatter.formatTime(screenState.time)!!
                    )
                )
                screenState.copy(shouldGoNext = true)
            }
        }
    }


    private fun tryToGetAlreadySetDateTime() {
        val dateTime = getTripDateTimeUseCase()
        if (dateTime != null)
            _state.update {
                val date = formatter.toRawDate(dateTime.date)
                val time = formatter.toRawTime(dateTime.time)
                it.copy(
                    date = date,
                    time = time,
                    isDateValid = true,
                    isTimeValid = true,
                    title = "${formatter.formatDate(date)}\n${formatter.formatTime(time)}"
                )
            }
    }
}