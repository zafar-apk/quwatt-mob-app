package profile.presentation

import core.domain.util.ImageCompressor
import core.domain.util.ImageFile
import core.domain.util.Resource
import core.domain.util.toCommonFlow
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import profile.data.remote.getuser.GetUser
import profile.data.remote.setphoto.SetUserPhoto
import user.domain.UserInteractor

class ProfileScreenViewModel(
    private val getUser: GetUser,
    private val setUserPhoto: SetUserPhoto,
    private val userInteractor: UserInteractor,
    private val imageCompressor: ImageCompressor,
    private val coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

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
                    shouldRegisterUser = false,
                    shouldRegisterTransport = false,
                    shouldOpenMyRequests = false,
                    shouldOpenMyTrips = false,
                    compressedImageNull = false
                )
            }

            else -> Unit
        }
    }

    private fun onOpenTransport() = _state.update { screenState ->
        when {
            _state.value.user?.licenceNumber.isNullOrEmpty() -> screenState.copy(
                shouldRegisterLicence = true
            )

            _state.value.user?.transport == null -> screenState.copy(
                shouldRegisterTransport = true
            )

            else -> screenState.copy(shouldOpenTransport = true)
        }
    }

    private fun setUserPhoto(photo: ImageFile) = viewModelScope.launch {
        _state.update {
            it.copy(isLoading = true)
        }
        withContext(Dispatchers.Default) {
            val compressedImage = imageCompressor.compressImage(photo)
            when (val result = compressedImage?.let { setUserPhoto.execute(it) }) {
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
                            isLoading = false,
                            user = result.data,
                        )
                    }
                }
                else -> {
                    _state.update {
                        it.copy(compressedImageNull = true, isLoading = false)
                    }
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
        _state.update { it.copy(isLoading = true, error = null) }

        _state.update {
            it.copy(
                isLoading = false,
                error = null,
                isNotAuthorized = userInteractor.isAuthorized().not()
            )
        }

        if (userInteractor.isAuthorized())
            when (val result = getUser.execute()) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            user = result.data!!,
                            isLoading = false,
                            error = null
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        if (result withCode HttpStatusCode.NotFound) {
                            it.copy(
                                isLoading = false,
                                shouldRegisterUser = true
                            )
                        } else {
                            it.copy(
                                error = result.throwable?.message.toString(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
    }
}