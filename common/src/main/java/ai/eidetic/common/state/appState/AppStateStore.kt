package ai.eidetic.common.state.appState

import arrow.optics.Lens

data class AppStateStore(
    private var appState: AppState = AppState.Initial
) {
    val state get() = appState

    fun <T> update(stateSelector: Lens<AppState, T>, newState: T) {
        appState = stateSelector.set(appState, newState)
        println("[State update]: $appState")
    }
}