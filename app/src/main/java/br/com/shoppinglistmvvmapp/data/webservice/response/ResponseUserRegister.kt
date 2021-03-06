package br.com.shoppinglistmvvmapp.data.webservice.response

import com.google.gson.annotations.SerializedName

data class ResponseUserRegister(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("response") val response: Response?,
    @SerializedName("status") val status: Int?,
    @SerializedName("message") val message: Message?
)