package br.com.shoppinglistmvvmapp.framework.presentation.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.shoppinglistmvvmapp.data.repository.UserRepository
import br.com.shoppinglistmvvmapp.framework.presentation.view.common.AbstractViewModel
import br.com.shoppinglistmvvmapp.framework.presentation.view.login.state.LoginViewState
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.safeRunOnUiThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel: AbstractViewModel() {

    private val _loginViewState = MutableLiveData<LoginViewState>()
    val loginViewState: LiveData<LoginViewState> = _loginViewState

    fun login(email: String, password: String){
        var isSuccess = false
        stateLoading()
        viewModelScope.launch(Dispatchers.IO) {
            isSuccess = onLoginAsync(email, password)
        }.invokeOnCompletion {
            safeRunOnUiThread {
                _loginViewState.value = if(isSuccess)
                    LoginViewState.Success
                else
                    LoginViewState.Error
            }
        }
    }

    private fun stateLoading(){
        _loginViewState.value = LoginViewState.Loading
    }

    private suspend fun onLoginAsync(email: String, password: String): Boolean{
        return UserRepository().loginAsync(email, password)
    }
}