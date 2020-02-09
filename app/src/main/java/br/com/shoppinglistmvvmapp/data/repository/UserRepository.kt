package br.com.shoppinglistmvvmapp.data.repository

import br.com.shoppinglistmvvmapp.data.webservice.NetworkServiceProvider
import br.com.shoppinglistmvvmapp.data.webservice.request.RequestLogin
import br.com.shoppinglistmvvmapp.data.webservice.request.RequestRegisterUser
import br.com.shoppinglistmvvmapp.utils.LoggedUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {
    private val networkServiceProvider = NetworkServiceProvider()

    suspend fun loginAsync(email: String, password: String): Boolean{
        return try{
            val response = withContext(Dispatchers.IO){
                networkServiceProvider.getService().login(RequestLogin(email, password)).execute()
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

    suspend fun registerUser(email: String, password: String, firstName: String, lastName: String): Boolean{
        return try{
            val response = withContext(Dispatchers.IO){
                networkServiceProvider.getService().registerUser(RequestRegisterUser(email, password, firstName, lastName)).execute()
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