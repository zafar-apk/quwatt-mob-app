package ui.passengers.all.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.domain.util.stringResource
import passengers.all.presentation.PassengersScreenEvent
import passengers.all.presentation.PassengersScreenState
import tj.ham_safar.app.android.core.presentation.components.ActionButton
import ui.core.presentation.components.ErrorView
import ui.core.presentation.components.MainButton
import ui.core.presentation.dialogs.RequestAuthorizationDialog
import ui.passengers.components.PassengerItem
import ui.passengers.core.shared_trip_filter.PassengerTripsFilterParcelize
import ui.passengers.core.shared_trip_filter.toPassengerTripsFilter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PassengersScreen(
    filterState: State<PassengerTripsFilterParcelize?>?,
    state: PassengersScreenState,
    onEvent: (PassengersScreenEvent) -> Unit
) {
//    val context = LocalContext.current
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = { onEvent(PassengersScreenEvent.LoadPassengers) }
    )
    LaunchedEffect(state) {
        when {
               state.shouldRegisterUserInfo -> {
//                Toast.makeText(context, "not_registered, Toast.LENGTH_SHORT).show()
                onEvent(PassengersScreenEvent.NavigateToProfileScreen)
                onEvent(PassengersScreenEvent.ResetNavigation)
            }

            state.openPassenger != null -> {
                onEvent(PassengersScreenEvent.NavigateToPassengerDetails(state.openPassenger!!))
                onEvent(PassengersScreenEvent.ResetNavigation)
            }

            state.openAddNewPassenger -> {
                onEvent(PassengersScreenEvent.NavigateToLeaveRequest)
                onEvent(PassengersScreenEvent.ResetNavigation)
            }
        }
    }

    LaunchedEffect(filterState) {
        val value: PassengerTripsFilterParcelize? = filterState?.value
        if (value != null)
            onEvent(PassengersScreenEvent.TripsFilterData(value.toPassengerTripsFilter()))
    }

    if (state.shouldRequestAuthorization) {
        RequestAuthorizationDialog(
            onDismissRequest = {},
            onLoginClicked = { onEvent(PassengersScreenEvent.NavigateToLoginScreen) }
        )
    }

    Scaffold(topBar = { PassengersTopBar(onEvent, state.city) }) {
        Box(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 21.dp)
                    .padding(top = 24.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .pullRefresh(pullRefreshState)
                    ) {
                        items(state.passengers) { passenger ->
                            PassengerItem(
                                fromAddress = passenger.addressFrom,
                                fromCity = passenger.cityFrom.name,
                                tripTime = passenger.time,
                                tripDate = passenger.date,
                                toCity = passenger.cityTo.name,
                                toAddress = passenger.addressTo,
                                minPrice = passenger.priceFrom,
                                maxPrice = passenger.priceTo,
                                passengersCount = passenger.count,
                                rating = passenger.rating,
                                avatar = passenger.user.photo,
                                name = passenger.user.name.orEmpty(),
                                onClick = {
                                    onEvent(
                                        PassengersScreenEvent.RequestToOpenPassengerDetails(
                                            passenger.id
                                        )
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        item {
                            state.errorMessage?.let { error ->
                                ErrorView(
                                    onRetry = { onEvent(PassengersScreenEvent.LoadPassengers) },
                                    modifier = Modifier.fillParentMaxSize(),
                                    error = error
                                )
                            }
                        }
                    }
                }

                if (state.errorMessage == null) {
                    MainButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        labelRes = "leave_a_request"
                    ) {
                        onEvent(PassengersScreenEvent.AddNewPassenger)
                    }
                }
            }

            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = state.isLoading,
                state = pullRefreshState
            )
        }
    }
}

@Composable
private fun PassengersTopBar(
    onEvent: (PassengersScreenEvent) -> Unit,
    fromCity: String?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp)
            .padding(top = 24.dp)
            .background(color = MaterialTheme.colors.background),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.size(32.dp))

        val city = fromCity ?: stringResource("from_all_cities")
        Text(
            text = stringResource("trips_from", listOf(city)),
            style = MaterialTheme.typography.body1.copy(fontSize = 20.sp)
        )

        ActionButton(
//            icon = Icons.Default.Tune,
            icon = Icons.Default.Done,
            onClick = {
                onEvent(PassengersScreenEvent.NavigateToFilters)
            },
            contentDescription = stringResource(id = "filters")
        )
    }
}