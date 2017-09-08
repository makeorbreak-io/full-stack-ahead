package xyz.fullstackahead.where2go.injection

import dagger.Component
import xyz.fullstackahead.where2go.ui.activity.MainActivity
import xyz.fullstackahead.where2go.Where2GoApp
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class
))
interface MainComponent {
    fun inject(app: Where2GoApp)
    fun inject(activity: MainActivity)
}
