package ui.edit.transport.presentation

import edit.transport.domain.EditTransportUseCase
import edit.transport.domain.GetTransportUseCase
import edit.transport.presentation.EditTransportEvent
import edit.transport.presentation.EditTransportViewModel
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class EditTransportAndroidViewModel (
    editTransport: EditTransportUseCase,
    getTransportUseCase: GetTransportUseCase
) : ViewModel() {

    private val viewModel = EditTransportViewModel(
        editTransportUseCase = editTransport,
        getTransportUseCase = getTransportUseCase,
        coroutineScope = viewModelScope
    )

    val state = viewModel.state

    fun onEvent(event: EditTransportEvent) = viewModel.onEvent(event)
}