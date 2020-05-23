package br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension

import android.os.Handler
import android.os.Looper

fun safeRunOnUiThread(run: () -> Unit){
    runOnUiThread{
        safeRun(run)
    }
}

fun runOnUiThread(run: () -> Unit){
    Handler(Looper.getMainLooper()).post(run)
}

fun safeRun(run: ()-> Unit){
    try{
        run()
    }catch (e: Exception){
        e.printStackTrace()
    }
}
