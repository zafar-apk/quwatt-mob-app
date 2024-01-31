package tj.ham_safar.app.trips.my_trips.presentation

import stations.all.domain.models.Station

data class MyTripsScreenState(
    val stations: List<Station> = emptyList(),
    val openTrip: Int? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)