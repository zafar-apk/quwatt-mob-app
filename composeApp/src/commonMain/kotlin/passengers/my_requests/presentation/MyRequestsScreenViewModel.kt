package passengers.my_requests.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import core.domain.util.Resource
import core.domain.util.toCommonStateFlow
import passengers.my_requests.domain.GetAllMyRequests

class MyRequestsScreenViewModel(
    private val getAllMyRequests: GetAllMyRequests,
    private val coroutineScope: CoroutineScope?
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private val _state = MutableStateFlow(MyRequestsScreenState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MyRequestsScreenState()
    ).toCommonStateFlow()

    init {
        getAllMyRequests()
    }

    fun onEvent(event: MyRequestsScreenEvent) {
        when (event) {
            is MyRequestsScreenEvent.RequestToOpenPassengerDetails ->
                _state.update { it.copy(openPassenger = event.id) }
            MyRequestsScreenEvent.ResetNavigation -> _state.update { it.copy(openPassenger = null) }
            MyRequestsScreenEvent.LoadMyRequests -> getAllMyRequests()
            else -> Unit
        }
    }

    private fun getAllMyRequests() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }
        when (val result = getAllMyRequests.execute()) {
            is Resource.Error -> _state.update {
                it.copy(
                    errorMessage = result.throwable?.message,
                    isLoading = false
                )
            }
            is Resource.Success -> _state.update {
                it.copy(
                    passengers = result.data.orEmpty(),
                    isLoading = false
                )
            }
        }
    }
}