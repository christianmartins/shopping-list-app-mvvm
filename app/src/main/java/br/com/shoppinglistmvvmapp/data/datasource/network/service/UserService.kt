package br.com.shoppinglistmvvmapp.data.datasource.network.service

import br.com.shoppinglistmvvmapp.data.datasource.network.dto.request.RequestLogin
import br.com.shoppinglistmvvmapp.data.datasource.network.dto.request.RequestRegisterUser
import br.com.shoppinglistmvvmapp.data.datasource.network.dto.response.*
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @POST("auth/login")
    fun login(
        @Body args: RequestLogin
    ): Call<ResponseLogin>

    @POST("user/register")
    fun registerUser(
        @Body args: RequestRegisterUser
    ): Call<ResponseUserRegister>
}