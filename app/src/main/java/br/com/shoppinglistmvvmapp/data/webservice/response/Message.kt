package br.com.shoppinglistmvvmapp.data.webservice.response

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("error") val error: String,
    @SerializedName("message") val message: String
)