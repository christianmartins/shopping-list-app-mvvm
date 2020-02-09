package br.com.shoppinglistmvvmapp.data.webservice.request

import br.com.shoppinglistmvvmapp.data.model.ItemShoppingList
import com.google.gson.annotations.SerializedName


data class RequestSaveItemShoppingList(
    @SerializedName("itemShoppingLists") val itemShoppingLists: List<ItemShoppingList>
)