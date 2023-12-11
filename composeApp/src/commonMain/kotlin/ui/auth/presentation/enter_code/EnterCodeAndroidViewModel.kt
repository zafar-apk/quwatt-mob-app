package ui.auth.presentation.enter_code

import auth.enter_code.domain.VerifyOtp
import auth.enter_code.presentation.EnterCodeScreenEvent
import auth.enter_code.presentation.EnterCodeScreenState
import auth.enter_code.presentation.EnterCodeViewModel
import core.domain.util.CommonStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import user.domain.UserInteractor

class EnterCodeAndroidViewModel (
    verifyOtp: VerifyOtp,
    userInteractor: UserInteractor,
) : ViewModel() {

    private val viewModel = EnterCodeViewModel(
        verifyOtp = verifyOtp,
        userInteractor = userInteractor,
        coroutineScope = viewModelScope
    )

    init {
//        stateHandle.get<String>(Arguments.PhoneNumber)?.let { phone ->
//            onEvent(EnterCodeScreenEvent.OnPhoneAvailable(phone))
//        }
    }

    val state: CommonStateFlow<EnterCodeScreenState> = viewModel.state

    fun onEvent(event: EnterCodeScreenEvent) = viewModel.onEvent(event)

}