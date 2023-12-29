package register.user.presentation

sealed interface RegisterUserScreenEvent {
    data class OnNameChanged(val name: String) : RegisterUserScreenEvent
    data class OnSurNameChanged(val surname: String) : RegisterUserScreenEvent
    data class OnPatronymicChanged(val patronymic: String) : RegisterUserScreenEvent
    data class OnDateOfBirthPicked(val dateOfBirth: String) : RegisterUserScreenEvent
    data class OnUserPhotoPicked(val imageFile: ByteArray) : RegisterUserScreenEvent
    data object Register : RegisterUserScreenEvent
    data object GoBack : RegisterUserScreenEvent
    data object CompleteRegistration : RegisterUserScreenEvent
    data class ChangeDateOfBirthDatePickerState(val isVisible: Boolean) : RegisterUserScreenEvent

}