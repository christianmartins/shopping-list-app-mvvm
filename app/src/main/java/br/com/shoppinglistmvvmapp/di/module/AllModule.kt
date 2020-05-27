package br.com.shoppinglistmvvmapp.di.module

fun getAllModulesList() = listOf(
    viewModelModule,
    viewUtilModule,
    repositoryModule,
    useCaseModule,
    networkModule,
    networkDataSourceModule
)


