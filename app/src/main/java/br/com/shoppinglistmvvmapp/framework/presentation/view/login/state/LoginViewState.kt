package br.com.shoppinglistmvvmapp.framework.presentation.view.login.state

sealed class LoginViewState {
    object Loading: LoginViewState()
    object Error: LoginViewState()
    object Success: LoginViewState()
}