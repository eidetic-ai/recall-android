package ai.eidetic.common.state.appAccessState

import arrow.optics.optics

@optics
data class AppAccessState(
    val email: String,
    val password: String,
){
    companion object{
        val Initial = AppAccessState(
            email = "",
            password = "",
        )
    }
}