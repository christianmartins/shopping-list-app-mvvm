package br.com.shoppinglistmvvmapp.data.webservice.request

import br.com.shoppinglistmvvmapp.domain.model.BaseModel
import com.google.gson.annotations.SerializedName

data class RequestRegisterUser(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String
): BaseModel()