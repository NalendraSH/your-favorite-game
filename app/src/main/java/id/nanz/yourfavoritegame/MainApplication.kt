package id.nanz.yourfavoritegame

import android.app.Application
import id.nanz.yourfavoritegame.core.BuildConfig
import id.nanz.yourfavoritegame.core.di.databaseModule
import id.nanz.yourfavoritegame.core.di.networkModule
import id.nanz.yourfavoritegame.core.di.repositoryModule
import id.nanz.yourfavoritegame.di.useCaseModule
import id.nanz.yourfavoritegame.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                )
            )
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}