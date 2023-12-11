package register.licence.presentation

data class RegisterUserLicenceScreenState(
    val licenceNumber: String = "",
    val expirationDate: String = "",
    val isLicenceRegistered: Boolean = false,
    val error: Throwable? = null,
    val isLoading: Boolean = false,
    val isLicenceNumberIsNotEntered: Boolean = false,
    val isExpirationDateIsNotEntered: Boolean = false
)