package ai.eidetic.recall.application

import ai.eidetic.appaccess.di.appAccessModule
import ai.eidetic.common.di.commonModule
import ai.eidetic.data.di.dataModule
import ai.eidetic.deckcreation.di.deckCreationModule
import ai.eidetic.domain.di.domainModule
import ai.eidetic.service.di.serviceModule
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RecallApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RecallApp)
            modules(
                commonModule,
                appAccessModule,
                deckCreationModule,
                domainModule,
                dataModule,
                serviceModule,
            )
        }
    }
}
