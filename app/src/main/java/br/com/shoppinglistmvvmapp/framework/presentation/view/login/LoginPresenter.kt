package br.com.shoppinglistmvvmapp.framework.presentation.view.login

import br.com.shoppinglistmvvmapp.data.repository.UserRepository

class LoginPresenter {

    suspend fun loginAsync(email: String, password: String): Boolean{
        return UserRepository().loginAsync(email, password)
    }
}