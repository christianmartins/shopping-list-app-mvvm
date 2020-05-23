package br.com.shoppinglistmvvmapp.framework

import android.app.Application
import android.content.Context
import br.com.shoppinglistmvvmapp.di.module.getAllModulesList
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        Stetho.initializeWithDefaults(this)

        startKoin {
            androidContext(this@App)
            modules(getAllModulesList())
        }
    }

    companion object {
        var context: Context? = null
    }
}