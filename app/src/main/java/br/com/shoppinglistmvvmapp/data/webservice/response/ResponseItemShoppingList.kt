package br.com.shoppinglistmvvmapp.data.webservice.response

import br.com.shoppinglistmvvmapp.domain.model.ItemShoppingList
import com.google.gson.annotations.SerializedName

data class  ResponseItemShoppingList(
    @SerializedName("itemShoppingLists") val itemsShoppingList: List<ItemShoppingList>
)