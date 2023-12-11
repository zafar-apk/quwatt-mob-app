package edit.licence.presentation

sealed class EditUserLicenceEvent {
    data class OnLicenceNumberChanged(val number: String) : EditUserLicenceEvent()
    data class OnExpirationDateChanged(val date: String) : EditUserLicenceEvent()
    object GoBack : EditUserLicenceEvent()
    object ResetState : EditUserLicenceEvent()
    object Edit: EditUserLicenceEvent()
}