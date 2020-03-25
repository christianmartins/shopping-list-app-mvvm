package br.com.shoppinglistmvvmapp.module

import br.com.shoppinglistmvvmapp.presentation.viewmodel.ShoppingListFragmentViewModel
import org.koin.dsl.module

val shoppingListFragmentViewModelModule = module {

    factory { ShoppingListFragmentViewModel() }

}