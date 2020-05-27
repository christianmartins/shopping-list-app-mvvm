package br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension


fun safeRun(onRun: () -> Unit){
    safeRun(onRun, null, null)
}

fun safeRun(
    onRun: () -> Unit,
    onError: ((exception: Exception) -> Unit)? = null,
    onCompletion: (()-> Unit)? = null
){
    try{
        onRun()
    }catch (e: Exception){
        e.printStackTrace()
        onError?.invoke(e)
    }finally {
        onCompletion?.invoke()
    }
}
