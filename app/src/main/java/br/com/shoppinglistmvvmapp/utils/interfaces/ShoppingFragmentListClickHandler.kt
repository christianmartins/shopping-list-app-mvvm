package br.com.shoppinglistmvvmapp.utils.interfaces

import br.com.shoppinglistmvvmapp.data.model.ShoppingList

interface ShoppingFragmentListClickHandler {
    fun onClickItemList(shoppingListId: String)
    fun onClickDeleteItemList(shoppingList: ShoppingList)
    fun onClickEditItemList(shoppingList: ShoppingList)
}
