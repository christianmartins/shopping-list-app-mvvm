package br.com.shoppinglistmvvmapp.presenter

import br.com.shoppinglistmvvmapp.data.repository.UserRepository

class UserRegistrationPresenter{


    suspend fun registerUser(email: String, password: String, firstName: String, lastName: String): Boolean {
        return UserRepository().registerUser(email, password, firstName, lastName)
    }

    suspend fun loginAsync(email: String, password: String): Boolean{
        return UserRepository().loginAsync(email, password)
    }

}