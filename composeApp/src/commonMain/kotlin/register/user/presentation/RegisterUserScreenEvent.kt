package register.user.presentation

import core.domain.util.ImageFile

sealed class RegisterUserScreenEvent {
    data class OnNameChanged(val name: String) : RegisterUserScreenEvent()
    data class OnSurNameChanged(val surname: String) : RegisterUserScreenEvent()
    data class OnPatronymicChanged(val patronymic: String) : RegisterUserScreenEvent()
    data class OnDateOfBirthPicked(val dateOfBirth: String) : RegisterUserScreenEvent()
    data class OnUserPhotoPicked(val imageFile: ImageFile) : RegisterUserScreenEvent()
    object Register : RegisterUserScreenEvent()
    object GoBack : RegisterUserScreenEvent()
    object CompleteRegistration : RegisterUserScreenEvent()

}