package auth.enter_phone.presentation

import auth.enter_phone.domain.SendOtp
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

class EnterPhoneViewModel(
    private val sendOtp: SendOtp,
) : ViewModel() {

    private val _state = MutableStateFlow(EnterPhoneScreenState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        EnterPhoneScreenState()
    ).toCommonStateFlow()

    fun onEvent(event: EnterPhoneScreenEvent) {
        when (event) {
            is EnterPhoneScreenEvent.OnPhoneTextChanged -> {
                _state.update { it.copy(phone = event.phone) }
            }

            EnterPhoneScreenEvent.Continue -> onContinue()

            EnterPhoneScreenEvent.ResetNavigation -> {
                _state.update { it.copy(next = false) }
            }
        }
    }

    private fun onContinue() {
        if (state.value.phone.length != 9) {
            _state.update {
                it.copy(
                    error = "Введите коректный номер телефона",
                    isLoading = false
                )
            }
            return
        }

        _state.update {
            it.copy(
                isLoading = true,
                error = null
            )
        }
        sendOtp()
    }

    private fun sendOtp(): Job = viewModelScope.launch {
        when (val result = sendOtp.execute(state.value.phone)) {
            is Resource.Success -> _state.update {
                it.copy(
                    isLoading = false,
                    error = null,
                    next = true
                )
            }

            is Resource.Error -> _state.update {
                it.copy(
                    isLoading = false,
                    error = result.throwable?.message,
                    next = false
                )
            }
        }
    }
}