package br.com.shoppinglistmvvmapp.view.activity

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {
    fun hideActionBar(){
        supportActionBar?.hide()
    }
}