package profile.presentation

sealed class ProfileScreenEvent {

    data class ChangeProfilePhoto(val photo: ByteArray) : ProfileScreenEvent()
    object OpenTransport : ProfileScreenEvent()
    object OpenLicence : ProfileScreenEvent()
    object OpenMyRequests : ProfileScreenEvent()
    object OpenMyTrips : ProfileScreenEvent()
    object NavigateToTransport : ProfileScreenEvent()
    object NavigateToLicence : ProfileScreenEvent()
    object NavigateToRegisterLicence : ProfileScreenEvent()
    object NavigateToRegisterTransport : ProfileScreenEvent()
    object Logout : ProfileScreenEvent()
    object LoadUser : ProfileScreenEvent()
    object OnLogin : ProfileScreenEvent()
    object ResetState : ProfileScreenEvent()
    object NavigateToMyRequests : ProfileScreenEvent()
    object NavigateToMyTrips : ProfileScreenEvent()
}