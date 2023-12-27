package register.licence.presentation

sealed interface RegisterUserLicenceEvent {
    data class OnLicenceNumberChanged(val number: String) : RegisterUserLicenceEvent
    data class OnExpirationDateChanged(val date: String) : RegisterUserLicenceEvent
    data object GoNext : RegisterUserLicenceEvent
    data object GoBack : RegisterUserLicenceEvent
    data object NavigateToRegisterTransport : RegisterUserLicenceEvent
    data object ResetState : RegisterUserLicenceEvent
    data object OpenDocumentExpirationDatePicker : RegisterUserLicenceEvent
    data object DismissDocumentDatePicker : RegisterUserLicenceEvent
}