package br.com.shoppinglistmvvmapp.data.mock

import br.com.shoppinglistmvvmapp.domain.model.ShoppingList
import br.com.shoppinglistmvvmapp.utils.DateUtils
import br.com.shoppinglistmvvmapp.utils.LoggedUser

object ShoppingListMock {

    fun getShoppingListData(numberOfItems: Int): List<ShoppingList>{
        val mockList = ArrayList<ShoppingList>()
        (0 until numberOfItems).forEach { i ->
            createShoppingList(title =  "Title $i", description = "Description %i").apply {
                mockList.add(this)
            }
        }
        return mockList
    }

    fun createShoppingList(
        title: String, description: String = "",
        currentItemsToComplete: Int = 0,
        totalItemsToComplete: Int = 0,
        deleted: Boolean = false,
        sent: Boolean = false
    ): ShoppingList{
        val dateTime = DateUtils.getDateTime()
        val userId = LoggedUser.getUserId()
        return ShoppingList(
            id = "SL_${userId}_${DateUtils.getTimeStamp()}",
            title = title,
            description = description,
            userId = userId,
            createAt = dateTime,
            updateAt = dateTime,
            authorName = LoggedUser.getAuthorName(),
            currentItemsToComplete = currentItemsToComplete,
            totalItemsToComplete = totalItemsToComplete,
            deleted = deleted,
            sent = sent
        )
    }
}