package ai.eidetic.service.di

import ai.eidetic.service.service.TextDetectionMLKitService
import ai.eidetic.service.service.TextDetectionService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val serviceModule = module {
    single<TextDetectionService> {
        TextDetectionMLKitService(
            context = androidContext(),
        )
    }
}
