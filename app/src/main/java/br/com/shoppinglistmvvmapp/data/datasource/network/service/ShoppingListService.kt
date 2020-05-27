package br.com.shoppinglistmvvmapp.data.datasource.network.service

import br.com.shoppinglistmvvmapp.data.datasource.network.dto.request.RequestSaveShoppingList
import br.com.shoppinglistmvvmapp.data.datasource.network.dto.response.ResponseSaveShoppingList
import br.com.shoppinglistmvvmapp.data.datasource.network.dto.response.ResponseShoppingList
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ShoppingListService {

    @POST("shopping-list/save")
    fun saveShoppingList(
        @Body args: RequestSaveShoppingList
    ): Call<ResponseSaveShoppingList>

    @GET("shopping-list/user/{userId}")
    fun getShoppingListByUser(
        @Path("userId") userId: Int
    ): Call<ResponseShoppingList>

}