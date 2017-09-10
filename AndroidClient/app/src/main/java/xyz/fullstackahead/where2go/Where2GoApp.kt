package xyz.fullstackahead.where2go

import android.app.Application
import xyz.fullstackahead.where2go.injection.AppModule
import xyz.fullstackahead.where2go.injection.DaggerMainComponent
import xyz.fullstackahead.where2go.injection.MainComponent
import xyz.fullstackahead.where2go.ui.activity.MainActivity

class Where2GoApp : Application() {

    companion object {
        lateinit var instance: Where2GoApp
    }

    val component: MainComponent by lazy {
        DaggerMainComponent.builder().appModule(AppModule(this)).build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    var mainActivity: MainActivity? = null
}
