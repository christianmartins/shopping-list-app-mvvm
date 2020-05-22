package br.com.shoppinglistmvvmapp.framework.presentation.model

data class ShoppingListPresentation (
    val id: String,
    val title: String,
    val description: String,
    val authorName: String,
    val date: String,
    val itemsForConclusion: String
): AbstractPresentation()