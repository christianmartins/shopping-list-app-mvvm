package br.com.shoppinglistmvvmapp.utils

import br.com.shoppinglistmvvmapp.utils.extension.isNotNullOrIsNotEmptyOrIsNotBlank
import br.com.shoppinglistmvvmapp.utils.extension.isValidEmail

class ValidateFieldUtils {

    fun isValidEmail(text: String): Boolean{
        return text.isNotNullOrIsNotEmptyOrIsNotBlank() && text.isValidEmail()
    }
    fun isValidPassword(text: String): Boolean{
        return text.isNotNullOrIsNotEmptyOrIsNotBlank() && text.length >= 6
    }

    fun isValidPasswordRepeat(text: String): Boolean{
        return text.isNotNullOrIsNotEmptyOrIsNotBlank() && text == text
    }

    fun isValidFirstName(text: String): Boolean{
        return text.isNotNullOrIsNotEmptyOrIsNotBlank() && text.length >= 3
    }

    fun isValidLastName(text: String): Boolean{
        return text.isNotNullOrIsNotEmptyOrIsNotBlank() && text.length >= 3
    }
}