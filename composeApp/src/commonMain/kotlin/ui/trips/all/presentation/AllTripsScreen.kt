package ui.trips.all.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch
import tj.ham_safar.app.android.core.presentation.components.ActionButton
import tj.quwatt.quwattapp.SharedRes
import trips.all.domain.models.Trip
import trips.all.presentation.TripsScreenEvent
import trips.all.presentation.TripsScreenState
import ui.core.presentation.components.ErrorView
import ui.core.presentation.components.MainButton
import ui.core.presentation.dialogs.RequestAuthorizationDialog
import ui.trips.components.TripItem
import ui.trips.core.models.TripsFilter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllTripsScreen(
    state: TripsScreenState,
    openTripsFilterForResult: suspend () -> TripsFilter?,
    onEvent: (TripsScreenEvent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = { onEvent(TripsScreenEvent.LoadTrips) }
    )
    LaunchedEffect(state) {
        when {
            state.shouldRegisterLicenceAndTransport -> {
                onEvent(TripsScreenEvent.NavigateToLicenceScreen)
                onEvent(TripsScreenEvent.ResetNavigation)
            }

            state.shouldRegisterUserInfo -> onRegisterUserInfo(onEvent)

            state.tripDetail != null -> {
                onEvent(TripsScreenEvent.NavigateTripItem(state.tripDetail))
                onEvent(TripsScreenEvent.ResetNavigation)
            }

            state.bookedTripDetail != null -> {
                onEvent(TripsScreenEvent.NavigateBookedTripItem(state.bookedTripDetail!!))
                onEvent(TripsScreenEvent.ResetNavigation)
            }

            state.createTrip -> {
                onEvent(TripsScreenEvent.NavigateToCreateTrip)
                onEvent(TripsScreenEvent.ResetNavigation)
            }
        }
    }

    if (state.shouldRegisterUserInfo) {
        Snackbar(content = {
            Text(core.domain.Strings.notRegistered)
        })
    }

    if (state.shouldRequestAuthorization) {
        RequestAuthorizationDialog(
            onLoginClicked = { onEvent(TripsScreenEvent.NavigateToLoginScreen) },
            onDismissRequest = { onEvent(TripsScreenEvent.CancelShowingAuthDialog) }
        )
    }

    Scaffold(
        topBar = {
            TripsTopBar(
                tripsFromCity = state.city,
                onEvent = onEvent,
                onOpenFilters = {
                    coroutineScope.launch {
                        onEvent(TripsScreenEvent.TripsFilterData(openTripsFilterForResult()))
                    }
                }
            )
        }
    ) {

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
                        item {
                            if (state.trips.isNotEmpty()) {
                                Text(
                                    modifier = Modifier.padding(top = 8.dp),
                                    text = stringResource(SharedRes.strings.all_trips),
                                    style = MaterialTheme.typography.subtitle1,
                                    fontSize = 18.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        items(state.trips, key = Trip::id) { trip ->
                            TripItem(
                                trip = trip,
                                onClick = { onEvent(TripsScreenEvent.RequestOpenTripItem(trip.id)) },
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        item {
                            state.error?.let { error ->
                                ErrorView(
                                    onRetry = { onEvent(TripsScreenEvent.LoadTrips) },
                                    modifier = Modifier.fillParentMaxSize(),
                                    error = error
                                )
                            }
                        }
                    }
                }

                if (state.error == null && state.isLoading.not()) {
                    MainButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        labelResource = SharedRes.strings.add_trip
                    ) {
                        onEvent(TripsScreenEvent.CreateTrip)
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

private fun onRegisterUserInfo(
    onEvent: (TripsScreenEvent) -> Unit
) {
    onEvent(TripsScreenEvent.NavigateToProfileScreen)
    onEvent(TripsScreenEvent.ResetNavigation)
}

@Composable
fun TripsTopBar(
    tripsFromCity: String?,
    onEvent: (TripsScreenEvent) -> Unit,
    onOpenFilters: () -> Unit
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

        val fromCity = tripsFromCity ?: stringResource(SharedRes.strings.from_all_cities)
        Text(
            text = stringResource(SharedRes.strings.trips_from, fromCity),
            style = MaterialTheme.typography.body1.copy(fontSize = 20.sp),
            textAlign = TextAlign.Center
        )
        ActionButton(
            icon = Icons.Default.Tune,
            onClick = { onOpenFilters() },
            contentDescription = stringResource(SharedRes.strings.filters)
        )
    }
}

//@Preview
//@Composable
//private fun AllTripsScreenPreview() {
//    HamSafarTheme {
//        AllTripsScreen(
//            null,
//            TripsScreenState(),
//            onEvent = {}
//        )
//    }
//}