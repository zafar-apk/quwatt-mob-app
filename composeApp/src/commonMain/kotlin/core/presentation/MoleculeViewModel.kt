package core.presentation

import androidx.compose.runtime.Composable
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

abstract class MoleculeViewModel<Event, Model>(
    initialEvent: Event?
) : ViewModel() {

    // Events have a capacity large enough to handle simultaneous UI events, but
    // small enough to surface issues if they get backed up for some reason.
    private val events = MutableSharedFlow<Event>(extraBufferCapacity = 20, replay = 1)

    init {
        initialEvent?.let(::take)
    }

    val models: StateFlow<Model> by lazy(LazyThreadSafetyMode.NONE) {
        viewModelScope.launchMolecule(mode = RecompositionMode.Immediate) {
            getState(events)
        }
    }

    fun take(event: Event) {
        if (!events.tryEmit(event)) {
            error("Event buffer overflow.")
        }
    }

    @Composable
    open fun getState(events: Flow<Event>): Model {
        throw NotImplementedError("You must override getState() function")
    }
}