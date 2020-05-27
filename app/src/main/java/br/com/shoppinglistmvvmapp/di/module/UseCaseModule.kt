package br.com.shoppinglistmvvmapp.di.module

import br.com.shoppinglistmvvmapp.data.repository.UserRepositoryImpl
import br.com.shoppinglistmvvmapp.domain.repository.UserRepository
import br.com.shoppinglistmvvmapp.domain.usecase.LoginUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { provideLoginUseCase(get()) }
}

private fun provideLoginUseCase(userRepository: UserRepository): LoginUseCase{
    return LoginUseCase(userRepository)
}