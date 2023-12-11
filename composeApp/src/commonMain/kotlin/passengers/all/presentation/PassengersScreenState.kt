package passengers.all.presentation

import passengers.all.domain.Passenger
import passengers.all.domain.PassengerFilter

data class PassengersScreenState(
    val passengers: List<Passenger> = emptyList(),
    val city: String? = null,
    val openPassenger: Int? = null,
    val isLoading: Boolean = false,
    val shouldRequestAuthorization: Boolean = false,
    val shouldRegisterUserInfo: Boolean = false,
    val openAddNewPassenger: Boolean = false,
    val errorMessage: String? = null,
    val tripsFilter: PassengerFilter? = null

)