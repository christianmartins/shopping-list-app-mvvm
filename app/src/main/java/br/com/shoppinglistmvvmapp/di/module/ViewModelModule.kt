package br.com.shoppinglistmvvmapp.di.module

import br.com.shoppinglistmvvmapp.domain.usecase.LoginUseCase
import br.com.shoppinglistmvvmapp.framework.presentation.view.itemshoppinglist.ItemShoppingListViewModel
import br.com.shoppinglistmvvmapp.framework.presentation.view.login.LoginViewModel
import br.com.shoppinglistmvvmapp.framework.presentation.view.shoppinglist.ShoppingListViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { provideLoginViewModel(get()) }
    factory { provideShoppingListFragmentViewModel() }
    factory { provideItemShoppingListViewModel() }
}

fun provideLoginViewModel(
    loginUseCase: LoginUseCase
): LoginViewModel {
    return LoginViewModel(loginUseCase)
}

fun provideShoppingListFragmentViewModel(
): ShoppingListViewModel {
    return ShoppingListViewModel()
}

fun provideItemShoppingListViewModel(
): ItemShoppingListViewModel {
    return ItemShoppingListViewModel()
}

