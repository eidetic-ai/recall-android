package ai.eidetic.learning.di

import ai.eidetic.common.state.learning.LearningViewStore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val learningModule = module {
    viewModel<LearningViewStore> {
        LearningViewStore()
    }
}