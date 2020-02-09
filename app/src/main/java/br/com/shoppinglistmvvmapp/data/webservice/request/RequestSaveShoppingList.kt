package br.com.shoppinglistmvvmapp.data.webservice.request

import br.com.shoppinglistmvvmapp.data.model.ShoppingList
import com.google.gson.annotations.SerializedName


data class RequestSaveShoppingList(
    @SerializedName("shoppingList") val shoppingList: List<ShoppingList>
)