package hamsafar_root

import core.domain.util.toCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import user.domain.UserRepository

class HamsafarRootViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HamsafarRootState())
    val state = _state.toCommonStateFlow()

    init {
        isFirstLaunch()
    }

    fun onEvent(event: HamsafarRootEvent) = when (event) {
        is HamsafarRootEvent.CheckIfTheFirstLaunch -> isFirstLaunch()
        is HamsafarRootEvent.SetFirstLaunchFalse -> setFirstLaunchFalse()
    }

    private fun isFirstLaunch() = viewModelScope.launch {
        val isFirst = withContext(Dispatchers.Default) {
            userRepository.getIsFirstLaunch()
        }
        _state.update { it.copy(isFirstLaunch = isFirst) }
    }

    private fun setFirstLaunchFalse() = viewModelScope.launch {
        userRepository.setIsFirstLaunch(isFirst = false)
        _state.update { it.copy(isFirstLaunch = false) }
    }
}