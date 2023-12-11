package ui.passengers.my_requests.presentation

import core.domain.util.CommonStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import passengers.my_requests.domain.GetAllMyRequests
import passengers.my_requests.presentation.MyRequestsScreenEvent
import passengers.my_requests.presentation.MyRequestsScreenState
import passengers.my_requests.presentation.MyRequestsScreenViewModel


class AllMyRequestsAndroidViewModel (
    getAllMyRequests: GetAllMyRequests
) : ViewModel() {

    private val viewModel = MyRequestsScreenViewModel(
        getAllMyRequests = getAllMyRequests,
        coroutineScope = viewModelScope
    )

    val state: CommonStateFlow<MyRequestsScreenState> = viewModel.state

    fun onEvent(event: MyRequestsScreenEvent) = viewModel.onEvent(event)
}