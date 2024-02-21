package hamsafar_root

import core.domain.util.toCommonStateFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import user.domain.UserRepository

class QuWattRootViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HamsafarRootState())
    val state = _state.toCommonStateFlow()

    init {
        isFirstLaunch()
    }

    fun onEvent(event: QuWattRootEvent) = when (event) {
        is QuWattRootEvent.CheckIfTheFirstLaunch -> isFirstLaunch()
        is QuWattRootEvent.SetFirstLaunchFalse -> setFirstLaunchFalse()
        is QuWattRootEvent.OnNewFcmToken -> updateFcmToken(event.token)
    }

    private fun updateFcmToken(token: String): Job = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            userRepository.saveNotificationToken(token)
        }
    }

    private fun isFirstLaunch() = viewModelScope.launch {
        val isFirst = withContext(Dispatchers.IO) {
            userRepository.getIsFirstLaunch()
        }
        _state.update { it.copy(isFirstLaunch = isFirst) }
    }

    private fun setFirstLaunchFalse() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            userRepository.setIsFirstLaunch(isFirst = false)
        }
        _state.update { it.copy(isFirstLaunch = false) }
    }
}