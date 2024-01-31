package tj.ham_safar.app.trips.create.date_time.presentation

sealed class TripDateTimeScreenEvent {

    object GoBack : TripDateTimeScreenEvent()

    object GoNext : TripDateTimeScreenEvent()

    object NavigateToTheNextScreen : TripDateTimeScreenEvent()

    object ResetState : TripDateTimeScreenEvent()

    object StartPickingDateTime : TripDateTimeScreenEvent()

    object StartPickingDate : TripDateTimeScreenEvent()

    data class OnTimeChanged(val time: String) : TripDateTimeScreenEvent()

    data class OnDateChanged(val date: String) : TripDateTimeScreenEvent()
}
