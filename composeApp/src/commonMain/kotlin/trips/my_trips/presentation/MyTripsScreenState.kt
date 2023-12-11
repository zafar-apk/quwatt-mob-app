package tj.ham_safar.app.trips.my_trips.presentation

import trips.all.domain.models.Trip

data class MyTripsScreenState(
    val trips: List<Trip> = emptyList(),
    val openTrip: Int? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)