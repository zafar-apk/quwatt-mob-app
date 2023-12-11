package passengers.detail.presentation

sealed class DetailedPassengerScreenEvent {
    object OnBackClick : DetailedPassengerScreenEvent()
    object WriteMessage : DetailedPassengerScreenEvent()
    object SendRequest : DetailedPassengerScreenEvent()
    data class LoadPassenger(val id: Int) : DetailedPassengerScreenEvent()
}