package tj.ham_safar.app.trips.create.driver.presentation

data class TripDriverScreenState(
    val count: Int = 1,
    val price: Int = 0,
    val error: String? = null,
    val shouldGoNext: Boolean = false,
    val shouldGoToTripLocation: Boolean = false,
    val shouldGoToTripDateTime: Boolean = false,
    val isRequestSent: Boolean = false

)