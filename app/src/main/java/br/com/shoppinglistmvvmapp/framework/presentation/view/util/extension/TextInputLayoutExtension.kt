package br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension

import br.com.shoppinglistmvvmapp.utils.ValidateFieldUtils
import com.google.android.material.textfield.TextInputLayout

private val validateFieldUtils by lazy {
    ValidateFieldUtils()
}

private fun TextInputLayout.isValid(isValid: Boolean, errorMessage: String): Boolean{
    return if(isValid){
        isErrorEnabled = false
        true
    }else{
        isErrorEnabled = true
        error = errorMessage
        false
    }
}

fun TextInputLayout.isValidEmail(email: String): Boolean{
    return isValid(validateFieldUtils.isValidEmail(email), "Esse email não é valido!")
}

fun TextInputLayout.isValidPassword(password: String): Boolean{
    return isValid(validateFieldUtils.isValidPassword(password), "Senha deve conter no minímo 6 caracteres!")
}

fun TextInputLayout.isValidPasswordRepeat(passwordRepeat: String): Boolean{
    return isValid(validateFieldUtils.isValidPasswordRepeat(passwordRepeat), "As senhas não são iguais!")
}

fun TextInputLayout.isValidFirstName(firstName: String): Boolean{
    return isValid(validateFieldUtils.isValidFirstName(firstName), "Primeiro nome deve conter no minímo três caracteres!")
}

fun TextInputLayout.isValidLastName(lastName: String): Boolean{
    return isValid(validateFieldUtils.isValidLastName(lastName), "Ultimo nome deve conter no minímo três caracteres!")
}