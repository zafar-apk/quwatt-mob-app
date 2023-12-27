package auth.enter_code.presentation

import auth.enter_code.domain.VerifyOtp
import core.domain.Strings
import core.domain.util.CommonStateFlow
import core.domain.util.Resource
import core.domain.util.toCommonStateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import profile.domain.User
import user.domain.UserInteractor

class EnterCodeViewModel(
    private val verifyOtp: VerifyOtp,
    private val userInteractor: UserInteractor
): ViewModel() {

    companion object {

        private const val VALID_CODE_LENGTH = 4
    }

    private val _state = MutableStateFlow(EnterCodeScreenState())
    val state: CommonStateFlow<EnterCodeScreenState> = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        EnterCodeScreenState()
    ).toCommonStateFlow()

    fun onEvent(event: EnterCodeScreenEvent) {
        when (event) {
            is EnterCodeScreenEvent.OnCodeChanged -> {
                _state.update { it.copy(code = event.code) }
            }

            EnterCodeScreenEvent.Continue -> {
                if (state.value.code.length != VALID_CODE_LENGTH) {
                    _state.update { it.copy(error = Strings.wrongCode) }
                    return
                }

                _state.update { it.copy(isLoading = true) }
                verifyOtp()
            }

            EnterCodeScreenEvent.ForgotPassword -> {
                _state.update { it.copy(navigation = EnterCodeNavigation.FORGOT_PASSWORD) }
            }

            EnterCodeScreenEvent.GoBack -> {
                _state.update { it.copy(navigation = EnterCodeNavigation.BACK) }
            }

            EnterCodeScreenEvent.ResetNavigation -> {
                _state.update { it.copy(navigation = null) }
            }

            is EnterCodeScreenEvent.OnPhoneAvailable -> {
                _state.update { it.copy(phone = event.phone) }
            }
        }
    }

    private fun verifyOtp(): Job = viewModelScope.launch {
        val result = verifyOtp.execute(
            otp = state.value.code
        )
        when (result) {
            is Resource.Success -> _state.update { screenState ->
                saveTokenIfExist(result.data?.token)

//                val navigation = if (result.data?.user == null) {
//                    EnterCodeNavigation.REGISTER
//                } else {
//                    EnterCodeNavigation.NEXT
//                }
                val navigation = EnterCodeNavigation.NEXT
                screenState.copy(
                    isLoading = false,
                    error = null,
                    navigation = navigation
                )
            }

            is Resource.Error -> _state.update {
                it.copy(
                    isLoading = false,
                    error = result.throwable?.message
                )
            }
        }
    }

    private suspend fun setIsUserExist(user: User?) = withContext(Dispatchers.Default) {
        userInteractor.setIsUserExist(user != null)
    }

    private suspend fun saveTokenIfExist(token: String?) = withContext(Dispatchers.Default) {
        token?.let { token ->
            userInteractor.saveToken(token)
        }
    }
}