package ui.register.user.presentation.user

import core.domain.util.CommonStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import register.user.domain.RegisterUser
import register.user.presentation.RegisterUserScreenEvent
import register.user.presentation.RegisterUserScreenState
import register.user.presentation.RegisterUserViewModel
import user.domain.UserInteractor

class RegisterUserAndroidViewModel (
    registerUser: RegisterUser,
    userInteractor: UserInteractor
) : ViewModel() {

    private val viewModel = RegisterUserViewModel(
        registerUser = registerUser,
        userInteractor = userInteractor,
        coroutineScope = viewModelScope
    )

    val state: CommonStateFlow<RegisterUserScreenState> = viewModel.state

    fun onEvent(event: RegisterUserScreenEvent) = viewModel.onEvent(event)
}