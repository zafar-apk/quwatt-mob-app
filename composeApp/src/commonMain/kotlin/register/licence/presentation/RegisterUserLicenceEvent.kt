package register.licence.presentation

sealed class RegisterUserLicenceEvent {
    data class OnLicenceNumberChanged(val number: String) : RegisterUserLicenceEvent()
    data class OnExpirationDateChanged(val date: String) : RegisterUserLicenceEvent()
    object GoNext : RegisterUserLicenceEvent()
    object GoBack : RegisterUserLicenceEvent()
    object NavigateToRegisterTransport : RegisterUserLicenceEvent()
    object ResetState : RegisterUserLicenceEvent()

}