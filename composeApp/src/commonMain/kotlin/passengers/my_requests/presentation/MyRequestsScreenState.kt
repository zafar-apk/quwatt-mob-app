package passengers.my_requests.presentation

import passengers.all.domain.Passenger

data class MyRequestsScreenState(
    val passengers: List<Passenger> = emptyList(),
    val openPassenger: Int? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)