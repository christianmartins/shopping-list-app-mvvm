package br.com.shoppinglistmvvmapp.data.webservice

import br.com.shoppinglistmvvmapp.data.webservice.request.RequestLogin
import br.com.shoppinglistmvvmapp.data.webservice.request.RequestRegisterUser
import br.com.shoppinglistmvvmapp.data.webservice.request.RequestSaveItemShoppingList
import br.com.shoppinglistmvvmapp.data.webservice.request.RequestSaveShoppingList
import br.com.shoppinglistmvvmapp.data.webservice.response.*
import retrofit2.Call
import retrofit2.http.*

interface ShoppingListAppApi {

    @POST("auth/login")
    fun login(
        @Body args: RequestLogin
    ): Call<ResponseLogin>

    @POST("user/register")
    fun registerUser(
        @Body args: RequestRegisterUser
    ): Call<ResponseUserRegister>

    @POST("shopping-list/save")
    fun saveShoppingList(
        @Body args: RequestSaveShoppingList
    ): Call<ResponseSaveShoppingList>

    @GET("shopping-list/user/{userId}")
    fun getShoppingListByUser(
        @Path("userId") userId: Int
    ): Call<ResponseShoppingList>

    @GET("item-shopping-list/shopping-list-id/{shoppingListId}")
    fun getItemsShoppingListByShoppingListId(
        @Path("shoppingListId") shoppingListId: String
    ): Call<ResponseItemShoppingList>

    @POST("item-shopping-list/save")
    fun saveItemsShoppingList(
        @Body args: RequestSaveItemShoppingList
    ): Call<ResponseSaveItemShoppingList>


}