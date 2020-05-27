package br.com.shoppinglistmvvmapp.data.datasource.network.dto.request

import br.com.shoppinglistmvvmapp.domain.model.ItemShoppingList
import com.google.gson.annotations.SerializedName


data class RequestSaveItemShoppingList(
    @SerializedName("itemShoppingLists") val itemShoppingLists: List<ItemShoppingList>
)