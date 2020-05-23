package br.com.shoppinglistmvvmapp.di.module

import br.com.shoppinglistmvvmapp.framework.presentation.viewmodel.ShoppingListFragmentViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { provideShoppingListFragmentViewModel() }
}

fun provideShoppingListFragmentViewModel(
): ShoppingListFragmentViewModel {
    return ShoppingListFragmentViewModel()
}
