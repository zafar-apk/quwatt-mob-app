package tj.ham_safar.app.trips.create.date_time.presentation

data class TripDateTimeScreenState(
    val time: String? = null,
    val date: String? = null,
    val title: String? = null,
    val isDateValid: Boolean = false,
    val isTimeValid: Boolean = false,
    val error: String? = null,
    val shouldGoNext: Boolean = false
)
