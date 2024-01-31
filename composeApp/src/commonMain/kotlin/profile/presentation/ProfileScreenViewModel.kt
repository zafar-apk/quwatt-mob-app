package profile.presentation

import core.domain.util.Resource
import core.domain.util.toCommonFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import profile.data.remote.getuser.GetUser
import profile.data.remote.setphoto.SetUserPhoto
import user.domain.UserInteractor

class ProfileScreenViewModel(
    private val getUser: GetUser,
    private val setUserPhoto: SetUserPhoto,
    private val userInteractor: UserInteractor
): ViewModel() {

    private val _state = MutableStateFlow(ProfileScreenState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ProfileScreenState()
    )
        .onStart { getUser() }
        .toCommonFlow()


    fun onEvent(event: ProfileScreenEvent) {
        when (event) {
            is ProfileScreenEvent.ChangeProfilePhoto -> setUserPhoto(event.photo)
            ProfileScreenEvent.Logout -> logout()
            ProfileScreenEvent.LoadUser -> getUser()
            ProfileScreenEvent.OpenTransport -> onOpenTransport()
            ProfileScreenEvent.OpenLicence -> _state.update { it.copy(shouldOpenLicence = true) }
            ProfileScreenEvent.OpenMyTrips -> _state.update { it.copy(shouldOpenMyTrips = true) }
            ProfileScreenEvent.OpenMyRequests -> _state.update { it.copy(shouldOpenMyRequests = true) }
            ProfileScreenEvent.ResetState -> _state.update {
                it.copy(
                    shouldOpenTransport = false,
                    shouldOpenLicence = false,
                    shouldRegisterLicence = false,
                    shouldRegisterTransport = false,
                    shouldOpenMyRequests = false,
                    shouldOpenMyTrips = false,
                )
            }

            else -> Unit
        }
    }

    private fun onOpenTransport() = _state.update { screenState ->
        when {
//            _state.value.user?.licenceNumber.isNullOrEmpty() -> screenState.copy(
//                shouldRegisterLicence = true
//            )
//
//            _state.value.user?.transport == null -> screenState.copy(
//                shouldRegisterTransport = true
//            )

            else -> screenState.copy(shouldOpenTransport = true)
        }
    }

    private fun setUserPhoto(photo: ByteArray) = viewModelScope.launch {
        _state.update {
            it.copy(isLoading = true)
        }

        when (val result = setUserPhoto.execute(photo)) {
            is Resource.Error -> _state.update {
                it.copy(
                    isLoading = false,
                    error = result.throwable?.message
                )
            }

            is Resource.Success -> {
                _state.update {
                    it.copy(
                        error = null,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun logout() = viewModelScope.launch {
        _state.update {
            it.copy(isLoading = true)
        }

        withContext(Dispatchers.Default) {
            userInteractor.logout()
        }

        _state.update {
            it.copy(
                isLoading = false,
                user = null,
                isNotAuthorized = true
            )
        }
    }

    private fun getUser() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true,
                error = null,
                isNotAuthorized = !userInteractor.isAuthorized()
            )
        }

        if (userInteractor.isAuthorized())
            when (val result = getUser.execute()) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            user = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = result.throwable?.message.toString(),
                            isLoading = false
                        )
                    }
                }
            }
    }
}