package br.com.shoppinglistmvvmapp.framework.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import br.com.shoppinglistmvvmapp.data.mock.ShoppingListMock
import br.com.shoppinglistmvvmapp.data.model.ShoppingList
import br.com.shoppinglistmvvmapp.data.repository.ShoppingListRepository
import br.com.shoppinglistmvvmapp.utils.GlobalUtils
import kotlinx.coroutines.Dispatchers

class ShoppingListFragmentViewModel: BaseViewModel() {

    private val shoppingListRepository = ShoppingListRepository()

    private val shoppingLists: LiveData<List<ShoppingList>> = liveData(Dispatchers.IO) {
        fetchShoppingListsByUser()
        emit(GlobalUtils.shoppingLists.toList())
    }

    fun createShoppingList(
        title: String,
        description: String = "",
        currentItemsToComplete: Int = 0,
        totalItemsToComplete: Int = 0,
        deleted: Boolean = false,
        sent: Boolean = false
    ) = ShoppingListMock.createShoppingList(title, description, currentItemsToComplete, totalItemsToComplete, deleted, sent)

    suspend fun sendShoppingList(){
        return shoppingListRepository.sendAndRefreshShoppingList()
    }

    fun getShoppingLists(): LiveData<List<ShoppingList>>{
        return shoppingLists
    }

    suspend fun fetchShoppingListsByUser(){
        shoppingListRepository.fetchByUser()
    }

    fun deleteItem(shoppingList: ShoppingList){
        return shoppingListRepository.markToDeleteItem(shoppingList)
    }

    fun getOrderedItems(): List<ShoppingList>{
        return shoppingListRepository.getOrderedItems()
    }

    fun updateTitle(newValue: String, shoppingList: ShoppingList){
        shoppingListRepository.updateTitle(newValue, shoppingList)
    }
}