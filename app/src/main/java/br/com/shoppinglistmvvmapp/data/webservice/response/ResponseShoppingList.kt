package br.com.shoppinglistmvvmapp.data.webservice.response

import br.com.shoppinglistmvvmapp.domain.model.ShoppingList
import com.google.gson.annotations.SerializedName

data class  ResponseShoppingList(
    @SerializedName("shoppingLists") val shoppingLists: List<ShoppingList>
)