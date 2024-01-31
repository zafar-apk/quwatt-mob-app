package edit.licence.presentation

import core.domain.Strings
import core.domain.util.Resource
import core.domain.util.toCommonStateFlow
import edit.licence.domain.EditLicenceUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import profile.data.remote.getuser.GetUser

class EditUserLicenceViewModel(
    private val getUser: GetUser,
    private val editLicenceUseCase: EditLicenceUseCase,
    private val coroutineScope: CoroutineScope?
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private val _state = MutableStateFlow(EditUserLicenceScreenState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        EditUserLicenceScreenState()
    ).toCommonStateFlow()


    init {
        loadUserLicence()
    }

    fun onEvent(event: EditUserLicenceEvent) {
        when (event) {
            EditUserLicenceEvent.Edit -> onEdit()
            EditUserLicenceEvent.ResetState -> _state.update {
                it.copy(
                    error = null,
                    isLoading = false,
                    isLicenceEdited = false
                )
            }

            is EditUserLicenceEvent.OnExpirationDateChanged -> {
                _state.update {
                    it.copy(
                        expirationDate = event.date,
                        isExpirationDateIsNotEntered = false
                    )
                }
            }

            is EditUserLicenceEvent.OnLicenceNumberChanged -> {
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

    private fun onEdit() = with(state.value) {
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
            editLicence()
        } else {
            _state.update {
                it.copy(
                    error = RuntimeException(Strings.enterData)
                )
            }
        }
    }

    private fun editLicence(): Job = viewModelScope.launch {
        val value = state.value
        _state.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }
        val result = editLicenceUseCase(
            licenceNumber = value.licenceNumber,
            licenceExpiration = value.expirationDate
        )
        when (result) {
            is Resource.Success -> _state.update {
                it.copy(
                    isLicenceEdited = true,
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

    private fun loadUserLicence() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, error = null) }

        when (val result = getUser.execute()) {
            is Resource.Success -> {
                val licence = result.data
                _state.update {
                    it.copy(
//                        licenceNumber = licence?.licenceNumber.orEmpty(),
//                        expirationDate = licence?.licenceExpiration.orEmpty(),
                        isLoading = false
                    )
                }
            }

            is Resource.Error -> _state.update {
                it.copy(error = result.throwable, isLoading = false)
            }
        }
    }
}