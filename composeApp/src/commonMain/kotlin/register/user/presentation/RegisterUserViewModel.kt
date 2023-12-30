package register.user.presentation

import core.domain.Strings
import core.domain.util.Resource
import core.domain.util.toCommonStateFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import profile.data.remote.setphoto.SetUserPhoto
import register.user.domain.RegisterUser
import user.domain.UserInteractor

class RegisterUserViewModel(
    private val registerUser: RegisterUser,
    private val userInteractor: UserInteractor,
    private val setUserPhoto: SetUserPhoto
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterUserScreenState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RegisterUserScreenState()
    ).toCommonStateFlow()

    fun onEvent(event: RegisterUserScreenEvent) {
        when (event) {
            is RegisterUserScreenEvent.ChangeDateOfBirthDatePickerState -> _state.update {
                it.copy(
                    isPickingDateOfBirth = event.isVisible
                )
            }

            is RegisterUserScreenEvent.OnDateOfBirthPicked -> {
                _state.update { it.copy(dateOfBirth = event.dateOfBirth) }
            }

            is RegisterUserScreenEvent.OnNameChanged -> _state.update {
                it.copy(name = event.name)
            }

            is RegisterUserScreenEvent.OnPatronymicChanged -> _state.update {
                it.copy(patronymic = event.patronymic)
            }

            is RegisterUserScreenEvent.OnSurNameChanged -> _state.update {
                it.copy(surName = event.surname)
            }

            is RegisterUserScreenEvent.OnUserPhotoPicked -> {
                uploadUserPhoto(event.imageFile)
                _state.update {
                    it.copy(
                        photo = event.imageFile,
                        isLoading = true
                    )
                }
            }

            RegisterUserScreenEvent.Register -> register()

            else -> Unit
        }
    }

    private fun uploadUserPhoto(photo: ByteArray) {
        viewModelScope.launch {
            val response = setUserPhoto.execute(photo)
            _state.update {
                if (response is Resource.Success && response.data == true) {
                    it.copy(isLoading = false)
                } else {
                    it.copy(
                        isLoading = false,
                        error = response.throwable ?: RuntimeException(Strings.unknownError)
                    )
                }
            }
        }
    }

    private fun register(): Job = viewModelScope.launch {
        if (isUserDataValid()) with(state.value) {
            _state.update { it.copy(isLoading = true) }
            val result = registerUser.execute(
                name = name,
                surname = surName,
                patronymic = patronymic,
                dateOfBirth = dateOfBirth,
            )
            when (result) {
                is Resource.Success -> {
                    val isRegistered = result.data ?: false
                    userInteractor.setIsUserExist(isRegistered)
                    _state.update {
                        it.copy(
                            registrationCompleted = isRegistered,
                            isLoading = false
                        )
                    }
                }

                is Resource.Error -> _state.update {
                    it.copy(
                        error = result.throwable,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun isUserDataValid(): Boolean {
        val value = state.value
        val isBlank = value.name.isBlank() ||
                value.surName.isBlank() ||
                value.patronymic.isBlank() ||
                value.dateOfBirth.isBlank()
        if (isBlank) _state.update {
            value.copy(error = Throwable(Strings.enterData))
        }
        return isBlank.not()
    }
}