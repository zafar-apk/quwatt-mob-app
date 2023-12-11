package tj.ham_safar.app.trips.create.location.presentation

sealed class TripLocationEvent {
    object ResetState : TripLocationEvent()
    object PickCityFrom : TripLocationEvent()
    object OnCancelPickingCityFrom : TripLocationEvent()
    data class OnCityFromPicked(val cityName: String) : TripLocationEvent()
    data class AddressFromTyped(val text: String) : TripLocationEvent()
    object PickCityTo : TripLocationEvent()
    object OnCancelPickingCityTo : TripLocationEvent()
    data class OnCityToPicked(val cityName: String) : TripLocationEvent()
    data class AddressToTyped(val text: String) : TripLocationEvent()
    object SwapLocations : TripLocationEvent()
    object GoNext : TripLocationEvent()
    object NavigateToTheNextScreen : TripLocationEvent()
    object GoBack : TripLocationEvent()
}