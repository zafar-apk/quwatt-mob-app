package passengers.all.presentation

import passengers.all.domain.PassengerFilter

sealed class PassengersScreenEvent {
    data class RequestToOpenPassengerDetails(val id: Int) : PassengersScreenEvent()
    data class NavigateToPassengerDetails(val id: Int) : PassengersScreenEvent()
    object NavigateToFilters : PassengersScreenEvent()
    object AddNewPassenger : PassengersScreenEvent()
    object ResetNavigation : PassengersScreenEvent()
    object NavigateToLoginScreen : PassengersScreenEvent()
    object OpenMenu : PassengersScreenEvent()
    object LoadPassengers : PassengersScreenEvent()
    object NavigateToLeaveRequest : PassengersScreenEvent()
    object NavigateToProfileScreen : PassengersScreenEvent()
    data class TripsFilterData(val filter: PassengerFilter) : PassengersScreenEvent()

}