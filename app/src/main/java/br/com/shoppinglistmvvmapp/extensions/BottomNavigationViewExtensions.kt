package br.com.shoppinglistmvvmapp.extensions

import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.hide(){
    setBottomNavigationMenuVisibility(View.GONE)
}

fun BottomNavigationView.show(){
    setBottomNavigationMenuVisibility(View.VISIBLE)
}

private fun BottomNavigationView.setBottomNavigationMenuVisibility(visibility: Int){
    this.visibility = visibility
}