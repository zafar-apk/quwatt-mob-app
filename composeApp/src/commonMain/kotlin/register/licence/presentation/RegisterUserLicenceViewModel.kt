package register.licence.presentation

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
import profile.data.remote.getuser.GetUser
import register.licence.domain.RegisterLicence

class RegisterUserLicenceViewModel(
    private val getUser: GetUser,
    private val registerLicence: RegisterLicence
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterUserLicenceScreenState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RegisterUserLicenceScreenState()
    ).toCommonStateFlow()

    init {
        checkHasUserAlreadyLicence()
    }

    fun onEvent(event: RegisterUserLicenceEvent) {
        when (event) {
            RegisterUserLicenceEvent.GoNext -> onGoNext()

            RegisterUserLicenceEvent.DismissDocumentDatePicker -> _state.update {
                it.copy(isPickingDocumentDate = false)
            }

            RegisterUserLicenceEvent.OpenDocumentExpirationDatePicker -> _state.update {
                it.copy(isPickingDocumentDate = true)
            }

            RegisterUserLicenceEvent.ResetState -> _state.update {
                it.copy(
                    error = null,
                    isLoading = false,
                    isLicenceRegistered = false
                )
            }

            is RegisterUserLicenceEvent.OnExpirationDateChanged -> {
                _state.update {
                    it.copy(
                        expirationDate = event.date,
                        isExpirationDateIsNotEntered = false
                    )
                }
            }

            is RegisterUserLicenceEvent.OnLicenceNumberChanged -> {
                _state.update {
                    it.copy(
                        licenceNumber = event.number,
                        isLicenceNumberIsNotEntered = false
                    )
                }
            }


            else -> Unit
        }
    }

    private fun onGoNext() = with(state.value) {
        var isValid = true
        if (licenceNumber.isEmpty()) {
            isValid = false
            _state.update {
                it.copy(isLicenceNumberIsNotEntered = true)
            }
        }
        if (expirationDate.isEmpty()) {
            isValid = false
            _state.update {
                it.copy(isExpirationDateIsNotEntered = true)
            }
        }

        if (isValid) {
            registerLicence()
        } else {
            _state.update {
                it.copy(
                    error = RuntimeException(Strings.enterData)
                )
            }
        }
    }

    private fun registerLicence(): Job = viewModelScope.launch {
        with(state.value) {
            _state.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }
            val result = registerLicence.execute(
                licenceNumber = licenceNumber,
                licenceExpiration = expirationDate
            )
            when (result) {
                is Resource.Success -> _state.update {
                    it.copy(
                        isLicenceRegistered = result.data ?: false,
                        isLoading = false
                    )
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

    private fun checkHasUserAlreadyLicence() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }

        val result = getUser.execute()
        when (result) {
            is Resource.Success -> {
//                val hasLicence = result.data?.licenceNumber != null
//                if (hasLicence)
//                    _state.update {
//                        it.copy(
//                            isLicenceRegistered = hasLicence,
//                            isLoading = false
//                        )
//                    }
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