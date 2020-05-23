package br.com.shoppinglistmvvmapp.framework.presentation.view.common

import androidx.appcompat.app.AppCompatActivity

abstract class AbstractActivity: AppCompatActivity() {
    fun hideActionBar(){
        supportActionBar?.hide()
    }
}