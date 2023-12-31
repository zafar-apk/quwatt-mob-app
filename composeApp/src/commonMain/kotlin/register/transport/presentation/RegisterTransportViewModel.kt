package register.transport.presentation

import core.domain.util.Resource
import core.domain.util.toCommonStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import register.transport.domain.RegisterTransport

class RegisterTransportViewModel(
    private val registerTransport: RegisterTransport,
): ViewModel() {

    private val _state = MutableStateFlow(RegisterTransportScreenState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RegisterTransportScreenState()
    ).toCommonStateFlow()

    fun onEvent(event: RegisterTransportEvent) {
        when (event) {
            RegisterTransportEvent.Save -> onGoNext()

            is RegisterTransportEvent.OnCapacityPicked -> _state.update {
                it.copy(
                    capacity = event.capacity,
                    capacityIsNotPicked = false
                )
            }

            is RegisterTransportEvent.OnDateOfIssueChanged -> _state.update {
                it.copy(
                    yearOfRelease = event.year,
                    yearIfIssueIsNotEntered = false
                )
            }

            is RegisterTransportEvent.OnHasACPicked -> _state.update {
                it.copy(hasAC = event.hasAC)
            }

            is RegisterTransportEvent.OnTransportBrandPicked -> _state.update {
                it.copy(
                    brand = event.brand,
                    isPickingBrand = false,
                    brandIsNotSelected = false
                )
            }

            is RegisterTransportEvent.OnTransportColorPicked -> _state.update {
                it.copy(
                    color = event.color,
                    isPickingColor = false,
                    colorIsNotPicked = false
                )
            }

            is RegisterTransportEvent.OnTransportModelChanged -> _state.update {
                it.copy(
                    model = event.model,
                    modelIsNotEntered = false
                )
            }

            is RegisterTransportEvent.OnTransportPhotoPicked -> _state.update {
                it.copy(photo = event.photo)
            }

            is RegisterTransportEvent.OnTransportTypePicked -> _state.update {
                it.copy(
                    type = event.transportType,
                    isPickingType = false,
                    typeIsNotSelected = false
                )
            }

            RegisterTransportEvent.ResetState -> _state.update {
                it.copy(isTransportRegistered = false)
            }

            RegisterTransportEvent.OnPickTransportBrand -> _state.update {
                it.copy(isPickingBrand = true)
            }

            RegisterTransportEvent.OnPickTransportColor -> _state.update {
                it.copy(isPickingColor = true)
            }

            RegisterTransportEvent.OnPickTransportType -> _state.update {
                it.copy(isPickingType = true)
            }

            RegisterTransportEvent.OnCancelPickingTransportType -> _state.update {
                it.copy(isPickingType = false)
            }

            RegisterTransportEvent.OnCancelPickingTransportBrand -> _state.update {
                it.copy(isPickingBrand = false)
            }

            else -> Unit
        }
    }

    private fun onGoNext() {
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
                registerTransport()
            }
        }
    }

    private fun registerTransport() = viewModelScope.launch {
        with(state.value) {
            val result = registerTransport.execute(
                type = type!!,
                brand = brand!!,
                model = model!!,
                colors = color!!,
                dateOfIssue = yearOfRelease.toString(),
                capacity = capacity!!,
                hasAC = hasAC,
                photo = photo
            )
            _state.update {
                it.copy(isLoading = true)
            }
            when (result) {
                is Resource.Error -> _state.update {
                    it.copy(
                        error = result.throwable,
                        isLoading = false
                    )
                }

                is Resource.Success -> _state.update {
                    it.copy(
                        isLoading = false,
                        isTransportRegistered = true
                    )
                }
            }
        }
    }
}