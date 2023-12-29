package ui.passengers.my_requests.presentation

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
import passengers.my_requests.presentation.MyRequestsScreenEvent
import passengers.my_requests.presentation.MyRequestsScreenState
import ui.core.presentation.components.BackButton
import ui.core.presentation.components.ErrorView
import ui.passengers.components.PassengerItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MyRequestsScreen(
    state: MyRequestsScreenState,
    onEvent: (MyRequestsScreenEvent) -> Unit
) {
    LaunchedEffect(state) {
        when {
            state.openPassenger != null -> {
                onEvent(MyRequestsScreenEvent.NavigateToPassengerDetails(state.openPassenger!!))
                onEvent(MyRequestsScreenEvent.ResetNavigation)
            }
        }
    }

    Scaffold(
        topBar = { MyRequestsTopBar(onGoBack = { onEvent(MyRequestsScreenEvent.GoBack) }) }
    ) {

        Box(modifier = Modifier.padding(it)) {

            val pullRefreshState = rememberPullRefreshState(
                refreshing = state.isLoading,
                onRefresh = { onEvent(MyRequestsScreenEvent.LoadMyRequests) }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 21.dp)
                    .padding(top = 24.dp)
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
                                MyRequestsScreenEvent.RequestToOpenPassengerDetails(passenger.id)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    state.errorMessage?.let { error ->
                        ErrorView(
                            onRetry = { onEvent(MyRequestsScreenEvent.LoadMyRequests) },
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
private fun MyRequestsTopBar(onGoBack: () -> Unit) {
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
            text = stringResource(id = "my_requests"),
            style = MaterialTheme.typography.body1.copy(fontSize = 20.sp)
        )
        Spacer(modifier = Modifier.size(32.dp))

    }
}