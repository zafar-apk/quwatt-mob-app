package ui.edit.licence.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import edit.licence.presentation.EditUserLicenceEvent
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder

internal const val editLicenceRoute: String = "edit-licence-route"

internal fun RouteBuilder.addToGraphEditLicenceScreen(
    onGoBack: () -> Unit,
) {
    scene(route = editLicenceRoute) {
        val viewModel = koinViewModel(EditLicenceAndroidViewModel::class)
        val state by viewModel.state.collectAsState()
        EditLicenceScreen(
            state = state,
            onEvent = { event ->
                when (event) {
                    EditUserLicenceEvent.GoBack -> onGoBack()
                    else -> viewModel.onEvent(event)
                }
            }
        )
    }
}

internal fun Navigator.navigateToEditLicenceScreen() =
    navigate(route = editLicenceRoute)