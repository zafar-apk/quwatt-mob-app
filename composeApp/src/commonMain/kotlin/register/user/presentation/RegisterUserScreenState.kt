package register.user.presentation

import core.domain.util.ImageFile

data class RegisterUserScreenState(
    val photo: ImageFile? = null,
    val name: String = "",
    val surName: String = "",
    val patronymic: String = "",
    val dateOfBirth: String = "",
    val registrationCompleted: Boolean = false,
    val error: Throwable? = null,
    val isLoading: Boolean = false
)