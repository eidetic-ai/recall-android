package ai.eidetic.common.di

import ai.eidetic.common.state.appState.AppStateStore
import org.koin.dsl.module

val commonModule = module {
    single<AppStateStore> { AppStateStore() }
}