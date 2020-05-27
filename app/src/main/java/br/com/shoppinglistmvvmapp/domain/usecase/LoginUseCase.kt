package br.com.shoppinglistmvvmapp.domain.usecase

import br.com.shoppinglistmvvmapp.domain.repository.UserRepository

class LoginUseCase(
    private val userRepository: UserRepository
) {

    suspend fun execute(email: String, password: String): Boolean{
        return userRepository.loginAsync(email, password)
    }
}