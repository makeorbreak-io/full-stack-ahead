package xyz.fullstackahead.where2go.injection

import dagger.Module
import dagger.Provides
import xyz.fullstackahead.where2go.Where2GoApp
import javax.inject.Singleton

@Module class AppModule(private val application: Where2GoApp) {

    @Provides @Singleton
    fun provideApplicationContext(): Where2GoApp {
        return application
    }
}
