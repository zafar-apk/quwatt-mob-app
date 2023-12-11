package edit.transport.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import core.domain.util.Resource
import core.domain.util.toCommonStateFlow
import edit.transport.domain.EditTransportUseCase
import edit.transport.domain.GetTransportUseCase
import edit.transport.domain.models.EditTransport

class EditTransportViewModel(
    private val getTransportUseCase: GetTransportUseCase,
    private val editTransportUseCase: EditTransportUseCase,
    private val coroutineScope: CoroutineScope?
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private val _state = MutableStateFlow(EditTransportScreenState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        EditTransportScreenState()
    ).toCommonStateFlow()

    init {
        loadUsersTransport()
    }

    fun onEvent(event: EditTransportEvent) {
        when (event) {
            EditTransportEvent.Edit -> onEdit()

            is EditTransportEvent.OnCapacityPicked -> _state.update {
                it.copy(
                    capacity = event.capacity,
                    capacityIsNotPicked = false
                )
            }

            is EditTransportEvent.OnDateOfIssueChanged -> _state.update {
                it.copy(
                    yearOfRelease = event.year,
                    yearIfIssueIsNotEntered = false
                )
            }

            is EditTransportEvent.OnHasACPicked -> _state.update {
                it.copy(hasAC = event.hasAC)
            }

            is EditTransportEvent.OnTransportBrandPicked -> _state.update {
                it.copy(
                    brand = event.brand,
                    isPickingBrand = false,
                    brandIsNotSelected = false
                )
            }

            is EditTransportEvent.OnTransportColorPicked -> _state.update {
                it.copy(
                    color = event.color,
                    isPickingColor = false,
                    colorIsNotPicked = false
                )
            }

            is EditTransportEvent.OnTransportModelChanged -> _state.update {
                it.copy(
                    model = event.model,
                    modelIsNotEntered = false
                )
            }

            is EditTransportEvent.OnTransportPhotoPicked -> _state.update {
                it.copy(photo = event.photo)
            }

            is EditTransportEvent.OnTransportTypePicked -> _state.update {
                it.copy(
                    type = event.transportType,
                    isPickingType = false,
                    typeIsNotSelected = false
                )
            }

            EditTransportEvent.ResetState -> _state.update {
                it.copy(isTransportEdited = false, error = null)
            }

            EditTransportEvent.OnPickTransportBrand -> _state.update {
                it.copy(isPickingBrand = true)
            }

            EditTransportEvent.OnPickTransportColor -> _state.update {
                it.copy(isPickingColor = true)
            }

            EditTransportEvent.OnPickTransportType -> _state.update {
                it.copy(isPickingType = true)
            }

            EditTransportEvent.OnCancelPickingTransportType -> _state.update {
                it.copy(isPickingType = false)
            }

            EditTransportEvent.OnCancelPickingTransportBrand -> _state.update {
                it.copy(isPickingBrand = false)
            }

            else -> Unit
        }
    }

    private fun onEdit() {
        with(state.value) {
            var isValid = true
            if (type == null) {
                isValid = false
                _state.update {
                    it.copy(typeIsNotSelected = true)
                }
            }
            if (brand == null) {
                isValid = false
                _state.update {
                    it.copy(brandIsNotSelected = true)
                }
            }
            if (model.isNullOrEmpty()) {
                isValid = false
                _state.update {
                    it.copy(modelIsNotEntered = true)
                }
            }
            if (color == null) {
                isValid = false
                _state.update {
                    it.copy(colorIsNotPicked = true)
                }
            }
            if (capacity == null) {
                isValid = false
                _state.update {
                    it.copy(capacityIsNotPicked = true)
                }
            }
            if (yearOfRelease == null) {
                isValid = false
                _state.update {
                    it.copy(yearIfIssueIsNotEntered = true)
                }
            }
            if (isValid) {
                editTransport()
            }
        }
    }

    private fun editTransport() = viewModelScope.launch {
        val value = state.value
        val transportId = value.transportId ?: return@launch
        _state.update { it.copy(isLoading = true) }
        val result = editTransportUseCase(
            EditTransport(
                id = transportId,
                type = value.type!!,
                brand = value.brand!!,
                model = value.model!!,
                colors = value.color!!,
                dateOfIssue = value.yearOfRelease.toString(),
                capacity = value.capacity!!,
                hasConditioner = value.hasAC,
            )
        )

        when (result) {
            is Resource.Error -> _state.update {
                it.copy(error = result.throwable, isLoading = false)
            }

            is Resource.Success -> _state.update {
                it.copy(isLoading = false, isTransportEdited = true)
            }
        }
    }

    private fun loadUsersTransport() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        val result = getTransportUseCase()
        when (result) {
            is Resource.Error -> _state.update {
                it.copy(error = result.throwable, isLoading = false)
            }

            is Resource.Success -> _state.update {
                val transport = result.data!!
                it.copy(
                    transportId = transport.id,
                    isLoading = false,
                    type = transport.type,
                    brand = transport.brand,
                    color = transport.colors,
                    model = transport.model,
                    yearOfRelease = transport.dateOfIssue.toIntOrNull(),
                    capacity = transport.capacity,
                    hasAC = transport.hasConditioner
                )
            }
        }
    }

}