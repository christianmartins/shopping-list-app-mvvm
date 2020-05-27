package br.com.shoppinglistmvvmapp.data.datasource.network.abstraction

interface UserNetworkDataSource{

    suspend fun loginAsync(email: String, password: String): Boolean

    suspend fun registerUser(email: String, password: String, firstName: String, lastName: String): Boolean
}