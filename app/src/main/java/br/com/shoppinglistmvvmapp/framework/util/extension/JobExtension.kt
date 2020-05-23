package br.com.shoppinglistmvvmapp.framework.util.extension

import kotlinx.coroutines.Job

fun Job.cancelIfActive(){
    if(isActive)
        cancel()
}