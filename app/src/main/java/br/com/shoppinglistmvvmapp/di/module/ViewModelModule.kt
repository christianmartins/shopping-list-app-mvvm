package br.com.shoppinglistmvvmapp.di.module

import br.com.shoppinglistmvvmapp.framework.presentation.view.shoppinglist.ShoppingListViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { provideShoppingListFragmentViewModel() }
}

fun provideShoppingListFragmentViewModel(
): ShoppingListViewModel {
    return ShoppingListViewModel()
}
