package ui.profile.presentation

import core.domain.util.ImageCompressor
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import profile.data.remote.getuser.GetUser
import profile.data.remote.setphoto.SetUserPhoto
import profile.presentation.ProfileScreenEvent
import profile.presentation.ProfileScreenViewModel
import user.domain.UserInteractor

class ProfileScreenAndroidViewModel (
    private val getUser: GetUser,
    private val setUserPhoto: SetUserPhoto,
    private val userInteractor: UserInteractor,
    private val imageCompressor: ImageCompressor
) : ViewModel() {

    private val viewModel = ProfileScreenViewModel(
        getUser = getUser,
        setUserPhoto = setUserPhoto,
        userInteractor = userInteractor,
        imageCompressor = imageCompressor,
        coroutineScope = viewModelScope
    )

    val state = viewModel.state

    fun onEvent(event: ProfileScreenEvent) = viewModel.onEvent(event)
}