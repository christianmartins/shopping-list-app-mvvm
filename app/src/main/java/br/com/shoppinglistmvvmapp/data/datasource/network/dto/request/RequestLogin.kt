package br.com.shoppinglistmvvmapp.data.datasource.network.dto.request

import com.google.gson.annotations.SerializedName

data class RequestLogin(
    @SerializedName("username") val email: String,
    @SerializedName("password") val password: String
)