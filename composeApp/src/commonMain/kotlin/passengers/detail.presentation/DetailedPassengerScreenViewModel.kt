package passengers.detail.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import core.domain.util.Resource
import core.domain.util.toCommonStateFlow
import passengers.detail.domain.GetPassengerById

class DetailedPassengerScreenViewModel(
    private val getPassengerById: GetPassengerById,
    private val coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private val _state = MutableStateFlow(DetailedPassengerScreenState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        DetailedPassengerScreenState()
    ).toCommonStateFlow()

    fun onEvent(event: DetailedPassengerScreenEvent) {
        when (event) {
            is DetailedPassengerScreenEvent.LoadPassenger -> getTripById(event.id)
            else -> Unit
        }
    }

    private fun getTripById(id: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    error = null,
                    isLoading = true
                )
            }
            when (val result = getPassengerById.execute(id)) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            passenger = result.data!!,
                            isLoading = false,
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = result.throwable?.message.toString(),
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }
}