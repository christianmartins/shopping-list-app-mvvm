package br.com.shoppinglistmvvmapp.data.repository

import br.com.shoppinglistmvvmapp.domain.model.ItemShoppingList
import br.com.shoppinglistmvvmapp.data.webservice.NetworkServiceProvider
import br.com.shoppinglistmvvmapp.data.webservice.request.RequestSaveItemShoppingList
import br.com.shoppinglistmvvmapp.utils.GlobalUtils
import br.com.shoppinglistmvvmapp.utils.LoggedUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItemShoppingListRepository {
    private val networkServiceProvider = NetworkServiceProvider()

    fun add(item: ItemShoppingList) {
        GlobalUtils.itemsShoppingList.add(item)
    }

    fun markToDeleteItem(item: ItemShoppingList) {
        GlobalUtils.itemsShoppingList.find { it.id == item.id }?.let{
            it.deleted = true
            it.sent = false
        }
    }

    fun getOrderedItems(shoppingListId: String): List<ItemShoppingList>{
        return orderingList(getItems(shoppingListId))
    }

    private fun orderingList(list: List<ItemShoppingList>): List<ItemShoppingList> {
        return list.sortedBy { it.createAt }
    }

    fun updateSelectedItem(selectedItem: ItemShoppingList, shoppingListId: String) {
        getItems(shoppingListId).let {list ->
            list.find { it.id == selectedItem.id }?.let {
                it.selected = selectedItem.selected
                it.sent = false
            }
        }
    }

    private fun getItems(shoppingListId: String): List<ItemShoppingList>{
        return GlobalUtils.itemsShoppingList.filter { it.shoppingListId == shoppingListId && !it.deleted }
    }

    suspend fun loadItemsShoppingListByShoppingListId(shoppingListId: String){
        try{
            if(LoggedUser.isLogged){
                val response = withContext(Dispatchers.IO){
                    networkServiceProvider.getService().getItemsShoppingListByShoppingListId(shoppingListId).execute()
                }
                response.body()?.itemsShoppingList?.let { itemsShoppingListNonNullable ->
                    val receivedItemsShoppingLists = itemsShoppingListNonNullable.onEach { it.sent = true }
                    filteredItemsShoppingList(receivedItemsShoppingLists, shoppingListId)
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun filteredItemsShoppingList(receivedItemsShoppingLists: List<ItemShoppingList>, shoppingListId: String){
        val oldList = ArrayList(GlobalUtils.itemsShoppingList)
        val newList = ArrayList<ItemShoppingList>()

        newList.addAll(oldList.filter { it.shoppingListId != shoppingListId })
        newList.addAll(receivedItemsShoppingLists)

        GlobalUtils.itemsShoppingList.clear()
        GlobalUtils.itemsShoppingList.addAll(newList.sortedBy { it.shoppingListId })

    }

    suspend fun sendAndRefreshItemShoppingList(shoppingListId: String){
        if(LoggedUser.isLogged){
            val listToSend = GlobalUtils.itemsShoppingList.filter { it.sent.not() && it.shoppingListId == shoppingListId}
            if(listToSend.isNotEmpty()){
                val isSuccess = send(listToSend)
                if(isSuccess){ listToSend.onEach { it.sent = true } }
            }
        }
    }

    private suspend fun send(itemsShoppingList: List<ItemShoppingList>): Boolean{
        return try{
            val listToSend = RequestSaveItemShoppingList(itemsShoppingList)
            val response = withContext(Dispatchers.IO){
                networkServiceProvider.getService().saveItemsShoppingList(listToSend).execute()
            }
            response.body()?.success?:false
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
    }
}