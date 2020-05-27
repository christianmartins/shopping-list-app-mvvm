package br.com.shoppinglistmvvmapp.framework.presentation.view.shoppinglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.shoppinglistmvvmapp.data.mock.ShoppingListMock
import br.com.shoppinglistmvvmapp.data.repository.ShoppingListRepository
import br.com.shoppinglistmvvmapp.domain.model.ShoppingList
import br.com.shoppinglistmvvmapp.framework.presentation.view.common.AbstractViewModel
import br.com.shoppinglistmvvmapp.framework.presentation.view.shoppinglist.mapper.ShoppingListMapper
import br.com.shoppinglistmvvmapp.framework.presentation.view.shoppinglist.state.ShoppingListViewState
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.safeRunOnUiThread
import br.com.shoppinglistmvvmapp.framework.util.extension.cancelIfActive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ShoppingListViewModel: AbstractViewModel() {

    private val shoppingListRepository = ShoppingListRepository()

    private val _shoppingListViewStateMutableLiveData = MutableLiveData<ShoppingListViewState>()
    val shoppingListViewState: LiveData<ShoppingListViewState> = _shoppingListViewStateMutableLiveData
    private var fetchJob: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetch()
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

    fun fetch(){
        isLoading()
        fetchJob?.cancelIfActive()
        fetchJob = viewModelScope.launch(Dispatchers.IO) {
            shoppingListRepository.fetchByUser()
            refresh()
        }
    }

    private fun isLoading(){
        safeRunOnUiThread {
            _shoppingListViewStateMutableLiveData.value = ShoppingListViewState.Loading
        }
    }

    private fun refresh(){
        safeRunOnUiThread {
            _shoppingListViewStateMutableLiveData.value = ShoppingListViewState.Success(convertToPresentationList())
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
        fetch()
    }

    fun add(shoppingList: ShoppingList){
        shoppingListRepository.add(shoppingList)
        fetch()
    }

}