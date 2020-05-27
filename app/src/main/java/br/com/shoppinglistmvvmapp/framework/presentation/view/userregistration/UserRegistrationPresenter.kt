package br.com.shoppinglistmvvmapp.framework.presentation.view.userregistration

class UserRegistrationPresenter{


    suspend fun registerUser(email: String, password: String, firstName: String, lastName: String): Boolean {
        return false //TODO *(temp)*//UserRepositoryImpl().registerUser(email, password, firstName, lastName)
    }

    suspend fun loginAsync(email: String, password: String): Boolean{
        return false //TODO *(temp)*//UserRepositoryImpl().loginAsync(email, password)
    }

}