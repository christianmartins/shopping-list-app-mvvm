package br.com.shoppinglistmvvmapp.extensions

import br.com.shoppinglistmvvmapp.App
import br.com.shoppinglistmvvmapp.R

fun String?.nonNullable(): String{
    return this?:""
}

fun String?.yesAnswer(): Boolean{
    return this.equals(App.context?.getString(R.string.speak_confirm).nonNullable(), true)
}

fun String?.isNullOrIsEmptyOrIsBlank(): Boolean{
    return this.isNullOrEmpty() || isNullOrBlank()
}

fun String?.isNotNullOrIsNotEmptyOrIsNotBlank(): Boolean{
    return isNullOrIsEmptyOrIsBlank().not()
}


fun String.isValidEmail(): Boolean{
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}