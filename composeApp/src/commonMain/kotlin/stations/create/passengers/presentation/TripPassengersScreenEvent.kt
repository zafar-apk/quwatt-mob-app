package tj.ham_safar.app.trips.create.passengers.presentation

sealed class TripPassengersScreenEvent {
    class GoBack(val route: String? = null) : TripPassengersScreenEvent()
    object GoNext : TripPassengersScreenEvent()
    object LeaveRequest : TripPassengersScreenEvent()
    object ResetState : TripPassengersScreenEvent()
    object OnCountPlus : TripPassengersScreenEvent()
    object OnCountMinus : TripPassengersScreenEvent()
    data class OnPriceFromChanged(val price: Int) : TripPassengersScreenEvent()
    data class OnPriceToChanged(val price: Int) : TripPassengersScreenEvent()
}