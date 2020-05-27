package br.com.shoppinglistmvvmapp.di.module

import br.com.shoppinglistmvvmapp.data.datasource.network.abstraction.UserNetworkDataSource
import br.com.shoppinglistmvvmapp.data.datasource.network.implementation.UserNetworkDataSourceImpl
import br.com.shoppinglistmvvmapp.data.datasource.network.service.UserService
import org.koin.dsl.module

val networkDataSourceModule = module {
    single { provideUserNetworkDataSource(get()) }
}

fun provideUserNetworkDataSource(userService: UserService): UserNetworkDataSource {
    return UserNetworkDataSourceImpl(userService)
}