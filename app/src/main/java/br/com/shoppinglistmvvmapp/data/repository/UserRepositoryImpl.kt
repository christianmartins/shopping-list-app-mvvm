package br.com.shoppinglistmvvmapp.data.repository

import br.com.shoppinglistmvvmapp.data.datasource.network.abstraction.UserNetworkDataSource
import br.com.shoppinglistmvvmapp.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userNetworkDataSource: UserNetworkDataSource
): UserRepository {

    override suspend fun loginAsync(email: String, password: String): Boolean {
        return userNetworkDataSource.loginAsync(email, password)
    }

    override suspend fun registerUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Boolean {
        return userNetworkDataSource.registerUser(
            email,
            password,
            firstName,
            lastName
        )
    }
}