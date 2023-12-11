package ui.passengers.my_requests.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import passengers.my_requests.presentation.MyRequestsScreenEvent

private const val myRequestsRoute = "my-requests-route"

fun RouteBuilder.addToGraphMyRequestsScreen(
    onNavigateToPassengerDetail: (id: Int) -> Unit,
    onGoBack: () -> Unit
) {

    scene(route = myRequestsRoute) {
        val viewModel: AllMyRequestsAndroidViewModel = koinViewModel(AllMyRequestsAndroidViewModel::class)
        val state by viewModel.state.collectAsState()
        MyRequestsScreen(
            state = state,
            onEvent = { event ->
                when (event) {
                    is MyRequestsScreenEvent.NavigateToPassengerDetails ->
                        onNavigateToPassengerDetail(event.id)
                    is MyRequestsScreenEvent.GoBack -> onGoBack()
                    else -> viewModel.onEvent(event)
                }
            }
        )
    }
}

fun Navigator.navigateToMyRequestsScreen() =
    navigate(route = myRequestsRoute)
