package ui.edit.licence.presentation

import edit.licence.domain.EditLicenceUseCase
import edit.licence.presentation.EditUserLicenceEvent
import edit.licence.presentation.EditUserLicenceViewModel
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import profile.data.remote.getuser.GetUser

class EditLicenceAndroidViewModel (
    getUser: GetUser,
    editLicenceUseCase: EditLicenceUseCase
) : ViewModel() {

    private val viewModel = EditUserLicenceViewModel(
        getUser,
        editLicenceUseCase,
        viewModelScope
    )

    val state = viewModel.state

    fun onEvent(event: EditUserLicenceEvent) = viewModel.onEvent(event)
}