package ai.eidetic.common.viewstore

import ai.eidetic.common.state.appState.AppState
import ai.eidetic.common.state.appState.AppStateStore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import arrow.optics.Lens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.updateAndGet
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class ViewStore<T>(
    private val stateSelector: Lens<AppState, T>,
) : ViewModel(), KoinComponent {

    private val appStateStore: AppStateStore by inject()

    private val _state = MutableStateFlow(stateSelector.get(appStateStore.state))

    val state @Composable get() = _state.asStateFlow().collectAsState().value

    @Composable
    operator fun invoke() = _state.asStateFlow().collectAsState().value

    protected fun update(block: (T) -> T) {
        appStateStore.update(stateSelector, _state.updateAndGet(block))
    }
}