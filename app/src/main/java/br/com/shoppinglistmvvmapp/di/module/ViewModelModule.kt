package br.com.shoppinglistmvvmapp.di.module

import br.com.shoppinglistmvvmapp.framework.presentation.view.itemshoppinglist.ItemShoppingListViewModel
import br.com.shoppinglistmvvmapp.framework.presentation.view.login.LoginViewModel
import br.com.shoppinglistmvvmapp.framework.presentation.view.shoppinglist.ShoppingListViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { provideLoginViewModel() }
    factory { provideShoppingListFragmentViewModel() }
    factory { provideItemShoppingListViewModel() }
}

fun provideLoginViewModel(
): LoginViewModel {
    return LoginViewModel()
}

fun provideShoppingListFragmentViewModel(
): ShoppingListViewModel {
    return ShoppingListViewModel()
}

fun provideItemShoppingListViewModel(
): ItemShoppingListViewModel {
    return ItemShoppingListViewModel()
}

