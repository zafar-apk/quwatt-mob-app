package ui.register.register_driver.licence.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import register.licence.presentation.RegisterUserLicenceEvent
import register.licence.presentation.RegisterUserLicenceViewModel

internal const val registerLicenceRoute: String = "register-licence-route"

internal fun RouteBuilder.addToGraphRegisterLicenceScreen(
    onGoBack: () -> Unit,
    onGoNext: () -> Unit
) {
    scene(route = registerLicenceRoute) {
        val viewModel = koinViewModel(RegisterUserLicenceViewModel::class)
        val state by viewModel.state.collectAsState()
        RegisterLicenceScreen(
            state = state,
            onEvent = { event ->
                when (event) {
                    RegisterUserLicenceEvent.GoBack -> onGoBack()
                    RegisterUserLicenceEvent.NavigateToRegisterTransport -> onGoNext()
                    else -> viewModel.onEvent(event)
                }
            }
        )
    }
}

internal fun Navigator.navigateToRegisterLicenceScreen() =
    navigate(route = registerLicenceRoute)