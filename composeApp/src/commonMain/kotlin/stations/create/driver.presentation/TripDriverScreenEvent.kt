package tj.ham_safar.app.trips.create.driver.presentation

sealed class TripDriverScreenEvent {
    class GoBack(val route: String? = null) : TripDriverScreenEvent()
    object GoNext : TripDriverScreenEvent()
    object CreateTrip : TripDriverScreenEvent()
    object ResetState : TripDriverScreenEvent()
    object OnCountPlus : TripDriverScreenEvent()
    object OnCountMinus : TripDriverScreenEvent()
    data class OnPriceChanged(val price: Int) : TripDriverScreenEvent()
}