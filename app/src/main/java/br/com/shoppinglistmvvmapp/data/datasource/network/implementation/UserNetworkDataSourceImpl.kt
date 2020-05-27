package br.com.shoppinglistmvvmapp.data.datasource.network.implementation

import br.com.shoppinglistmvvmapp.data.datasource.network.abstraction.UserNetworkDataSource
import br.com.shoppinglistmvvmapp.data.datasource.network.dto.request.RequestLogin
import br.com.shoppinglistmvvmapp.data.datasource.network.dto.request.RequestRegisterUser
import br.com.shoppinglistmvvmapp.data.datasource.network.service.UserService
import br.com.shoppinglistmvvmapp.utils.LoggedUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserNetworkDataSourceImpl(
    private val userService: UserService
): UserNetworkDataSource {

    override suspend fun loginAsync(email: String, password: String): Boolean{
        return try{
            val response = withContext(Dispatchers.IO){
                userService.login(RequestLogin(email, password)).execute()
            }

            if(response.isSuccessful){
                val token = response.body()?.token
                val user = response.body()?.user

                if(token != null && user != null){
                    LoggedUser.isLogged(token, user)
                }else{
                    LoggedUser.clear()
                }

                token != null
            }else{
                false
            }
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    override suspend fun registerUser(email: String, password: String, firstName: String, lastName: String): Boolean{
        return try{
            val response = withContext(Dispatchers.IO){
                userService.registerUser(RequestRegisterUser(email, password, firstName, lastName)).execute()
            }

            if(response.isSuccessful){
                response.body()?.success?:false
            }else{
                false
            }
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

}