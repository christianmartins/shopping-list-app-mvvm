package br.com.shoppinglistmvvmapp.data.datasource.network.dto.response

import com.google.gson.annotations.SerializedName

data class ResponseSaveShoppingList(
    @SerializedName("success") val success: Boolean?
)
