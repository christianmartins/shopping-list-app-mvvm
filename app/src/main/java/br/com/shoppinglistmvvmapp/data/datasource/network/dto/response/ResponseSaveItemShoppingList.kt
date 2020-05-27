package br.com.shoppinglistmvvmapp.data.datasource.network.dto.response

import com.google.gson.annotations.SerializedName

data class ResponseSaveItemShoppingList(
    @SerializedName("success") val success: Boolean?
)
