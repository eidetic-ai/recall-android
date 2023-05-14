package ai.eidetic.common.state.appAccessState

import ai.eidetic.common.state.appState.AppState
import ai.eidetic.common.state.appState.appAccessState
import ai.eidetic.common.viewstore.ViewStore

class AppAccessViewStore(): ViewStore<AppAccessState>(AppState.appAccessState){
    fun logIn(email: String, password: String){
        update { state ->
            state.copy(
                email = email,
                password = password,
            )
        }
    }
}