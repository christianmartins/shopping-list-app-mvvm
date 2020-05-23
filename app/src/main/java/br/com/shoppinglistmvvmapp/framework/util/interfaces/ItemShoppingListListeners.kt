package br.com.shoppinglistmvvmapp.framework.util.interfaces

import br.com.shoppinglistmvvmapp.domain.model.ItemShoppingList

interface ItemShoppingListListeners {
    fun deleteItem(item: ItemShoppingList)
    fun onSelectedItem(item: ItemShoppingList)
}