package br.com.shoppinglistmvvmapp.presentation.converter

import br.com.shoppinglistmvvmapp.App
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.data.model.ShoppingList
import br.com.shoppinglistmvvmapp.extensions.nonNullable
import br.com.shoppinglistmvvmapp.utils.DateUtils

data class ShoppingListPresentation (
    var id: String,
    var title: String,
    var description: String,
    var authorName: String,
    var date: String,
    var itemsForConclusion: String
): BasePresentation() {
    companion object{
        fun convert(shoppingList: ShoppingList): ShoppingListPresentation{
            return ShoppingListPresentation(
                id = shoppingList.id,
                title = shoppingList.title,
                description = shoppingList.title,
                authorName = App.context?.getString(
                    R.string.created_by, 
                    shoppingList.authorName
                ).nonNullable(),
                date = DateUtils.getFormatDateTime(shoppingList.createAt),
                itemsForConclusion = App.context?.getString(
                    R.string.shopping_list_current_progress,
                    shoppingList.currentItemsToComplete, 
                    shoppingList.totalItemsToComplete
                ).nonNullable()
            )
        }
    }
}