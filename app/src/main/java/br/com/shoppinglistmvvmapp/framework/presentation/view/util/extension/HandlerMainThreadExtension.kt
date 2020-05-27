package br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension

import android.os.Handler
import android.os.Looper

fun safeRunOnUiThread(onRun: () -> Unit) {
    safeRunOnUiThread(onRun, null, null)
}

fun safeRunOnUiThread(
    onRun: () -> Unit,
    onError: ((exception: Exception) -> Unit)? = null,
    onCompletion: (()-> Unit)? = null
){
    runOnUiThread{
        safeRun(onRun, onError, onCompletion)
    }
}

fun runOnUiThread(onRun: () -> Unit){
    Handler(Looper.getMainLooper()).post(onRun)
}
