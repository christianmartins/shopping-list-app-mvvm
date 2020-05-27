package br.com.shoppinglistmvvmapp.data.datasource.network.service

import br.com.shoppinglistmvvmapp.data.datasource.network.dto.request.RequestSaveItemShoppingList
import br.com.shoppinglistmvvmapp.data.datasource.network.dto.response.ResponseItemShoppingList
import br.com.shoppinglistmvvmapp.data.datasource.network.dto.response.ResponseSaveItemShoppingList
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ItemShoppingListService {

    @GET("item-shopping-list/shopping-list-id/{shoppingListId}")
    fun getItemsShoppingListByShoppingListId(
        @Path("shoppingListId") shoppingListId: String
    ): Call<ResponseItemShoppingList>

    @POST("item-shopping-list/save")
    fun saveItemsShoppingList(
        @Body args: RequestSaveItemShoppingList
    ): Call<ResponseSaveItemShoppingList>

}