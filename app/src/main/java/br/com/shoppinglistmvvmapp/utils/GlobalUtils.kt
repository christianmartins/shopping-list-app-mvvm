package br.com.shoppinglistmvvmapp.utils

import br.com.shoppinglistmvvmapp.framework.App
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.domain.model.ItemShoppingList
import br.com.shoppinglistmvvmapp.domain.model.ShoppingList
import br.com.shoppinglistmvvmapp.utils.extension.nonNullable

object GlobalUtils {

    const val googleRecognitionServiceName = "com.google.android.googlequicksearchbox/com.google.android.voicesearch.serviceapi.GoogleRecognitionService"
    val shoppingLists = ArrayList<ShoppingList>()
    val itemsShoppingList = ArrayList<ItemShoppingList>()
    var fragmentAlive = ""
    val urlApp = App.context?.getString(R.string.url_app).nonNullable()

    fun clearLists(){
        shoppingLists.clear()
        itemsShoppingList.clear()
    }
}