package ui.passengers.all.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import passengers.all.presentation.PassengersScreenEvent
import passengers.all.presentation.PassengersScreenState

const val allPassengersRoute = "passengers"
const val allPassengersFilterKey = "passengers-filter"

fun RouteBuilder.addToGraphAllPassengersScreen(
    onNavigateToFilter: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToLeaveRequest: () -> Unit,
    onNavigateToPassengerDetails: (passengerId: Int) -> Unit,
    onNavigateToProfile: () -> Unit,
//    onGetFilterResult: LiveData<PassengerTripsFilterParcelize>?
) {
    scene(route = allPassengersRoute) {
        val viewModel: AllPassengersAndroidViewModel = koinViewModel(AllPassengersAndroidViewModel::class)
        val state by viewModel.state.collectAsState(PassengersScreenState())
//        val filterResultState = onGetFilterResult?.observeAsState()

        PassengersScreen(
//            filterState = filterResultState,
            filterState = null,
            state = state
        ) { event ->
            when (event) {
                is PassengersScreenEvent.NavigateToFilters -> onNavigateToFilter()
                is PassengersScreenEvent.NavigateToLoginScreen -> onNavigateToLogin()
                is PassengersScreenEvent.NavigateToLeaveRequest -> onNavigateToLeaveRequest()
                is PassengersScreenEvent.NavigateToPassengerDetails ->
                    onNavigateToPassengerDetails(event.id)
                is PassengersScreenEvent.NavigateToProfileScreen -> onNavigateToProfile()
                else -> viewModel.onEvent(event)
            }
        }
    }
}

fun Navigator.navigateToAllPassengersScreen() = navigate(
    route = allPassengersRoute
)