package ui.stations.create.driver.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.path
import tj.ham_safar.app.trips.create.driver.presentation.TripDriverScreenEvent
import ui.core.presentation.StringRouteBuilder
import ui.core.presentation.changePlaceHoldersToArgs

private const val completeActionArg = "complete-action"

private val tripDriverRoute: String =
    StringRouteBuilder("trip-create-driver")
        .addArgPlaceHolder(completeActionArg)
        .build()

fun RouteBuilder.addToGraphTripDriverScreen(
    onGoBack: (route: String?) -> Unit,
    onSuccessfullyTripCreated: () -> Unit
) { scene(route = tripDriverRoute
    ) {
        val completeActionArg = it.path<Int>(completeActionArg)!!
        val viewModel: TripDriverAndroidViewModel = koinViewModel(TripDriverAndroidViewModel::class)
        val state by viewModel.state.collectAsState()
        TripDriverScreen(state = state) { event ->
            when (event) {
                is TripDriverScreenEvent.GoBack -> onGoBack(event.route)
                is TripDriverScreenEvent.GoNext -> onSuccessfullyTripCreated()
                else -> viewModel.onEvent(event)
            }
        }
    }
}

fun Navigator.navigateToTripDriverScreen(completeAction: Int) = navigate(
    route = tripDriverRoute.changePlaceHoldersToArgs(
        completeActionArg to completeAction.toString()
    )
)
