package passengers.detail.presentation

import passengers.all.domain.Passenger

data class DetailedPassengerScreenState(
    val passenger: Passenger? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
)