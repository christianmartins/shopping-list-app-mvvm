package br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension

import android.view.View

fun View.setEmptyList(size: Int){
    visibility = if(size == 0){
        View.VISIBLE
    }else{
        View.GONE
    }
}