package ui.register.register_driver.transport.presentation

import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import register.transport.domain.RegisterTransport
import register.transport.presentation.RegisterTransportEvent
import register.transport.presentation.RegisterTransportViewModel

class RegisterTransportAndroidViewModel (
    private val registerTransport: RegisterTransport
) : ViewModel() {

    private val viewModel = RegisterTransportViewModel(
        registerTransport = registerTransport,
        coroutineScope = viewModelScope
    )

    val state = viewModel.state

    fun onEvent(event: RegisterTransportEvent) = viewModel.onEvent(event)
}