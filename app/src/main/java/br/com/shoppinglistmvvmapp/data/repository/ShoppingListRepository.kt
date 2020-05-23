package br.com.shoppinglistmvvmapp.data.repository

import br.com.shoppinglistmvvmapp.domain.model.ShoppingList
import br.com.shoppinglistmvvmapp.data.webservice.NetworkServiceProvider
import br.com.shoppinglistmvvmapp.data.webservice.request.RequestSaveShoppingList
import br.com.shoppinglistmvvmapp.utils.GlobalUtils
import br.com.shoppinglistmvvmapp.utils.LoggedUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShoppingListRepository {

    private val networkServiceProvider = NetworkServiceProvider()

    private fun findShoppingList(currentShoppingListId: String): ShoppingList?{
        return GlobalUtils.shoppingLists.find { it.id == currentShoppingListId }
    }

    fun updateShoppingListTotalItems(shoppingListId: String, newTotal: Int){
        findShoppingList(shoppingListId)?.let {
            it.totalItemsToComplete = newTotal
        }
    }

    suspend fun sendAndRefreshShoppingList(){
        if(LoggedUser.isLogged){
            val listToSend = GlobalUtils.shoppingLists.filter { it.sent.not() }
            if(listToSend.isNotEmpty()){
                val isSuccess = send(listToSend)
                if(isSuccess){ listToSend.onEach { it.sent = true } }
            }
        }
    }

    private suspend fun send(shoppingLists: List<ShoppingList>): Boolean{
        return try{
            val listToSend = RequestSaveShoppingList(shoppingLists)
            val response = withContext(Dispatchers.IO){
                networkServiceProvider.getService().saveShoppingList(listToSend).execute()
            }
            response.body()?.success?:false
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    suspend fun loadListByUser(){
        try{
            LoggedUser.user?.let {user ->
                val response = withContext(Dispatchers.IO){
                    networkServiceProvider.getService().getShoppingListByUser(user.id).execute()
                }
                response.body()?.shoppingLists?.let { shoppingListsNonNullable ->
                    val receivedShoppingList = shoppingListsNonNullable.onEach { it.sent = true }
                    GlobalUtils.shoppingLists.clear()
                    GlobalUtils.shoppingLists.addAll(receivedShoppingList)
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    suspend fun fetchByUser(){
        try{
            LoggedUser.user?.let {user ->
                val response = withContext(Dispatchers.IO){
                    networkServiceProvider.getService().getShoppingListByUser(user.id).execute()
                }
                response.body()?.shoppingLists?.let { shoppingListsNonNullable ->
                    val receivedShoppingList = shoppingListsNonNullable.onEach { it.sent = true }
                    GlobalUtils.shoppingLists.clear()
                    GlobalUtils.shoppingLists.addAll(receivedShoppingList)
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun markToDeleteItem(shoppingList: ShoppingList) {
        GlobalUtils.shoppingLists.find { it.id == shoppingList.id }?.let{
            it.deleted = true
            it.sent = false
        }
    }

    fun getOrderedItems(): List<ShoppingList>{
        return orderingList(getItems())
    }

    private fun orderingList(list: List<ShoppingList>): List<ShoppingList> {
        return list.sortedBy { it.createAt }
    }

    private fun getItems(): List<ShoppingList>{
        return GlobalUtils.shoppingLists.filter { !it.deleted }
    }

    fun updateTitle(newValue: String, shoppingList: ShoppingList){
        GlobalUtils.shoppingLists.find { it.id == shoppingList.id }?.let{
            it.title = newValue
            it.sent = false
        }
    }

}