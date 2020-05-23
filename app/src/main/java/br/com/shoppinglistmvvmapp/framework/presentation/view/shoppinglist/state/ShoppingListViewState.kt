package br.com.shoppinglistmvvmapp.framework.presentation.view.shoppinglist.state

import br.com.shoppinglistmvvmapp.framework.presentation.view.shoppinglist.model.ShoppingListPresentation

sealed class ShoppingListViewState {

    object Loading: ShoppingListViewState()
    object Error: ShoppingListViewState()

    data class Success(
        val shoppingListPresentationList: List<ShoppingListPresentation>
    ): ShoppingListViewState()

}