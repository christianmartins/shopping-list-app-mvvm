package br.com.shoppinglistmvvmapp.data.mock

import br.com.shoppinglistmvvmapp.domain.model.ItemShoppingList
import br.com.shoppinglistmvvmapp.utils.DateUtils

object ItemShoppingListMock {

    fun getItemShoppingListData(numberOfItems: Int, shoppingListId: String = ""): List<ItemShoppingList>{
        val mockList = ArrayList<ItemShoppingList>()
        (0 until numberOfItems).forEach { _ ->
            createItemShoppingList(
                shoppingListId = shoppingListId,
                description = "Description"
            ).apply {
                mockList.add(this)
            }
        }
        return mockList
    }

    fun createItemShoppingList(
        shoppingListId: String,
        description: String,
        selected: Boolean = false,
        deleted: Boolean = false,
        sent: Boolean = false
    ): ItemShoppingList{
        val dateTime = DateUtils.getDateTime()
        return ItemShoppingList(
            id = "{$shoppingListId}_ISL_${DateUtils.getTimeStamp()}",
            description = description,
            shoppingListId = shoppingListId,
            selected = selected,
            deleted = deleted,
            createAt = dateTime,
            updateAt = dateTime,
            sent = sent
        )
    }
}