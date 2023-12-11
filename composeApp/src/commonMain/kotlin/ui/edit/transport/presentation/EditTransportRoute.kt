package ui.edit.transport.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import edit.transport.presentation.EditTransportEvent
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder

private const val editTransportRoute: String = "edit-transport-route"

internal fun RouteBuilder.addToGraphEditTransportScreen(
    onGoBack: () -> Unit,
) {
    scene(route = editTransportRoute) {
        val viewModel = koinViewModel(EditTransportAndroidViewModel::class)
        val state by viewModel.state.collectAsState()
        EditTransportScreen(
            state = state,
            onEvent = { event ->
                when (event) {
                    EditTransportEvent.GoBack -> onGoBack()
                    else -> viewModel.onEvent(event)
                }
            }
        )
    }
}

internal fun Navigator.navigateToEditTransportScreen() =
    navigate(route = editTransportRoute)
