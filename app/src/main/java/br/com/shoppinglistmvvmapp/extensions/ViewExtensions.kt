package br.com.shoppinglistmvvmapp.extensions

import android.view.View

fun View.setEmptyList(size: Int){
    visibility = if(size == 0){
        View.VISIBLE
    }else{
        View.GONE
    }
}