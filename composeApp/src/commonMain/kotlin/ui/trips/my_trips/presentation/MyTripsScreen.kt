package ui.trips.my_trips.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.domain.util.stringResource
import ui.core.presentation.components.BackButton
import tj.ham_safar.app.trips.my_trips.presentation.MyTripsScreenState
import trips.all.domain.models.Trip
import trips.my_trips.presentation.MyTripsScreenEvent
import ui.core.presentation.components.ErrorView
import ui.trips.components.TripItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyTripsScreen(
    state: MyTripsScreenState,
    onEvent: (MyTripsScreenEvent) -> Unit
) {
    LaunchedEffect(state) {
        when {
            state.openTrip != null -> {
                onEvent(MyTripsScreenEvent.NavigateToTripDetails(state.openTrip!!))
                onEvent(MyTripsScreenEvent.ResetNavigation)
            }
        }
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = { onEvent(MyTripsScreenEvent.LoadMyTrips) }
    )

    Scaffold(
        topBar = { MyTripsTopBar(onGoBack = { onEvent(MyTripsScreenEvent.GoBack) }) }
    ) {

        Box(modifier = Modifier.padding(it)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 21.dp)
                    .padding(top = 24.dp)
                    .pullRefresh(pullRefreshState)
            ) {
                items(state.trips, key = Trip::id) { trip ->
                    TripItem(
                        trip = trip,
                        onClick = { onEvent(MyTripsScreenEvent.RequestToOpenTripDetails(trip.id)) },
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    state.errorMessage?.let { error ->
                        ErrorView(
                            onRetry = { onEvent(MyTripsScreenEvent.LoadMyTrips) },
                            modifier = Modifier.fillParentMaxSize(),
                            error = error
                        )
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
private fun MyTripsTopBar(onGoBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.background)
            .padding(horizontal = 21.dp)
            .padding(top = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackButton { onGoBack() }
        Text(
            text = stringResource(id = "my_trips"),
            style = MaterialTheme.typography.body1.copy(fontSize = 20.sp)
        )
        Spacer(modifier = Modifier.size(32.dp))

    }
}