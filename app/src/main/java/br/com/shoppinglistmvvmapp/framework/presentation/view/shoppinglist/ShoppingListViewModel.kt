package br.com.shoppinglistmvvmapp.framework.presentation.view.shoppinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.shoppinglistmvvmapp.data.mock.ShoppingListMock
import br.com.shoppinglistmvvmapp.data.model.ShoppingList
import br.com.shoppinglistmvvmapp.data.repository.ShoppingListRepository
import br.com.shoppinglistmvvmapp.framework.presentation.view.shoppinglist.mapper.ShoppingListMapper
import br.com.shoppinglistmvvmapp.framework.presentation.view.shoppinglist.model.ShoppingListPresentation
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.safeRunOnUiThread
import br.com.shoppinglistmvvmapp.framework.presentation.view.common.AbstractViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingListViewModel: AbstractViewModel() {

    private val shoppingListRepository = ShoppingListRepository()
    private val _shoppingListPresentationListLiveData = MutableLiveData<List<ShoppingListPresentation>>()
    private val shoppingListPresentationListLiveData: LiveData<List<ShoppingListPresentation>> = _shoppingListPresentationListLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchShoppingListsByUser()
        }
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

    fun getShoppingListPresentationList(): LiveData<List<ShoppingListPresentation>>{
        return shoppingListPresentationListLiveData
    }

    suspend fun fetchShoppingListsByUser(){
        shoppingListRepository.fetchByUser()
        refresh()
    }

    private fun refresh(){
        safeRunOnUiThread {
            _shoppingListPresentationListLiveData.value = convertToPresentationList()
        }
    }

    private fun convertToPresentationList() = getOrderedItems().map {
        ShoppingListMapper.convert(it)
    }

    fun deleteItem(shoppingList: ShoppingList){
        return shoppingListRepository.markToDeleteItem(shoppingList)
    }

    private fun getOrderedItems(): List<ShoppingList>{
        return shoppingListRepository.getOrderedItems()
    }

    fun updateTitle(newValue: String, shoppingList: ShoppingList){
        shoppingListRepository.updateTitle(newValue, shoppingList)
    }
}