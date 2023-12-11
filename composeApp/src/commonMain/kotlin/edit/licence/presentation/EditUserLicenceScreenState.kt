package edit.licence.presentation

data class EditUserLicenceScreenState(
    val licenceNumber: String = "",
    val expirationDate: String = "",
    val isLicenceEdited: Boolean = false,
    val error: Throwable? = null,
    val isLoading: Boolean = false,
    val isLicenceNumberIsNotEntered: Boolean = false,
    val isExpirationDateIsNotEntered: Boolean = false
)