package ui.register.register_driver.transport.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import register.transport.presentation.RegisterTransportEvent
import register.transport.presentation.RegisterTransportViewModel

private const val registerTransportRoute: String = "register-transport-route"

internal fun RouteBuilder.addToGraphRegisterTransportScreen(
    onGoBack: () -> Unit,
    onTransportRegistered: () -> Unit
) {
    scene(route = registerTransportRoute) {
        val viewModel = koinViewModel(RegisterTransportViewModel::class)
        val state by viewModel.state.collectAsState()
        RegisterTransportScreen(
            state = state,
            onEvent = { event ->
                when (event) {
                    RegisterTransportEvent.GoBack -> onGoBack()
                    RegisterTransportEvent.NavigateToProfile -> onTransportRegistered()
                    else -> viewModel.onEvent(event)
                }
            }
        )
    }
}

internal fun Navigator.navigateToRegisterTransportScreen() =
    navigate(route = registerTransportRoute)
