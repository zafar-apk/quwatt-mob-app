package ui.trips.all.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import trips.all.presentation.TripsScreenEvent
import trips.all.presentation.TripsScreenState
import trips.all.presentation.TripsScreenViewModel
import ui.trips.core.models.TripsFilter

const val allTripsRoute = "trips"

fun RouteBuilder.addToGraphAllTripsScreen(
    onNavigateToCreateTrip: () -> Unit,
    onNavigateToTripsFilterForResult: suspend () -> TripsFilter?,
    onNavigateToTripItem: (tripItemId: Int) -> Unit,
    onNavigateToBookedTripItem: (tripItemId: Int) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToLicence: () -> Unit
) {
    scene(route = allTripsRoute) {
        val viewModel = koinViewModel(TripsScreenViewModel::class)
        val state by viewModel.models.collectAsStateWithLifecycle(TripsScreenState())
        AllTripsScreen(
            state = state,
            openTripsFilterForResult = onNavigateToTripsFilterForResult,
            onEvent = { event ->
                when (event) {
                    is TripsScreenEvent.NavigateToCreateTrip -> onNavigateToCreateTrip()
                    is TripsScreenEvent.NavigateTripItem -> onNavigateToTripItem(event.id)
                    is TripsScreenEvent.NavigateBookedTripItem -> onNavigateToBookedTripItem(event.id)
                    is TripsScreenEvent.NavigateToLoginScreen -> onNavigateToLogin()
                    is TripsScreenEvent.NavigateToProfileScreen -> onNavigateToProfile()
                    is TripsScreenEvent.NavigateToLicenceScreen -> onNavigateToLicence()
                    else -> viewModel.onEvent(event)
                }
            }
        )
    }
}
