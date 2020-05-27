package br.com.shoppinglistmvvmapp.domain.repository

interface UserRepository {

    suspend fun loginAsync(email: String, password: String): Boolean

    suspend fun registerUser(email: String, password: String, firstName: String, lastName: String): Boolean

}