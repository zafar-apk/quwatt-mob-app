package stations.my_trips.presentation

sealed class MyTripsScreenEvent {
    data class RequestToOpenTripDetails(val id: Int) : MyTripsScreenEvent()
    data class NavigateToTripDetails(val id: Int) : MyTripsScreenEvent()
    object ResetNavigation: MyTripsScreenEvent()
    object LoadMyTrips : MyTripsScreenEvent()
    object GoBack : MyTripsScreenEvent()

}