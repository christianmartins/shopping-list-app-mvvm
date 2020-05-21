package br.com.shoppinglistmvvmapp.utils

import br.com.shoppinglistmvvmapp.framework.util.enum.ActionType

data class RecognitionParams (
    var actionsType: ActionType = ActionType.NONE,
    var currentShoppingListId: String = ""
)