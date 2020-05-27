package br.com.shoppinglistmvvmapp.di.module

import br.com.shoppinglistmvvmapp.data.datasource.network.abstraction.UserNetworkDataSource
import br.com.shoppinglistmvvmapp.data.repository.UserRepositoryImpl
import br.com.shoppinglistmvvmapp.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { provideUserRepository(get()) }
}

private fun provideUserRepository(userNetworkDataSource: UserNetworkDataSource): UserRepository {
    return UserRepositoryImpl(userNetworkDataSource)
}