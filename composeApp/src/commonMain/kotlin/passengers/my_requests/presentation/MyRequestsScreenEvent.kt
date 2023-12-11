package passengers.my_requests.presentation

sealed class MyRequestsScreenEvent {
    data class RequestToOpenPassengerDetails(val id: Int) : MyRequestsScreenEvent()
    data class NavigateToPassengerDetails(val id: Int) : MyRequestsScreenEvent()
    object ResetNavigation: MyRequestsScreenEvent()
    object LoadMyRequests : MyRequestsScreenEvent()
    object GoBack : MyRequestsScreenEvent()

}