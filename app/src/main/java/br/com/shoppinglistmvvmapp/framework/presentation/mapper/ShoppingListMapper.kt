package br.com.shoppinglistmvvmapp.framework.presentation.mapper

import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.data.model.ShoppingList
import br.com.shoppinglistmvvmapp.framework.App
import br.com.shoppinglistmvvmapp.framework.presentation.model.ShoppingListPresentation
import br.com.shoppinglistmvvmapp.utils.DateUtils
import br.com.shoppinglistmvvmapp.utils.extension.nonNullable

object ShoppingListMapper: AbstractMapper<ShoppingList, ShoppingListPresentation>() {

    override fun convert(entity: ShoppingList): ShoppingListPresentation {
        return with(entity){
            ShoppingListPresentation(
                id = id,
                title = title,
                description = title,
                authorName = App.context?.getString(
                    R.string.created_by,
                    authorName
                ).nonNullable(),
                date = DateUtils.getFormatDateTime(entity.createAt),
                itemsForConclusion = App.context?.getString(
                    R.string.shopping_list_current_progress,
                    currentItemsToComplete,
                    totalItemsToComplete
                ).nonNullable()
            )
        }
    }
}