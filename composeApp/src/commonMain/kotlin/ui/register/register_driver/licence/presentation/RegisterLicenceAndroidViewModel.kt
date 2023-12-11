package ui.register.register_driver.licence.presentation

import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import profile.data.remote.getuser.GetUser
import register.licence.domain.RegisterLicence
import register.licence.presentation.RegisterUserLicenceEvent
import register.licence.presentation.RegisterUserLicenceViewModel

class RegisterLicenceAndroidViewModel (
    getUser: GetUser,
    registerLicence: RegisterLicence
) : ViewModel() {

    private val viewModel = RegisterUserLicenceViewModel(
        getUser,
        registerLicence,
        viewModelScope
    )

    val state = viewModel.state

    fun onEvent(event: RegisterUserLicenceEvent) = viewModel.onEvent(event)
}