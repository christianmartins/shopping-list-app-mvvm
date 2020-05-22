package br.com.shoppinglistmvvmapp.framework.presentation.view.activity

import androidx.appcompat.app.AppCompatActivity

abstract class AbstractActivity: AppCompatActivity() {
    fun hideActionBar(){
        supportActionBar?.hide()
    }
}