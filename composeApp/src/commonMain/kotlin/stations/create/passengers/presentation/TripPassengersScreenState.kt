package tj.ham_safar.app.trips.create.passengers.presentation

data class TripPassengersScreenState(
    val count: Int = 1,
    val priceFrom: Int = 0,
    val priceTo: Int = 0,
    val error: String? = null,
    val shouldGoNext: Boolean = false,
    val shouldGoToTripLocation: Boolean = false,
    val shouldGoToTripDateTime: Boolean = false,
    val isRequestSent: Boolean = false
)