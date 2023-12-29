package register.user.presentation

data class RegisterUserScreenState(
    val photo: ByteArray? = null,
    val name: String = "",
    val surName: String = "",
    val patronymic: String = "",
    val dateOfBirth: String = "",
    val registrationCompleted: Boolean = false,
    val error: Throwable? = null,
    val isLoading: Boolean = false,
    val isPickingDateOfBirth: Boolean = false
)