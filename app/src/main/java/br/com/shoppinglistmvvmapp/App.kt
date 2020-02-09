package br.com.shoppinglistmvvmapp

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        Stetho.initializeWithDefaults(this)
    }

    companion object {
        var context: Context? = null
    }
}