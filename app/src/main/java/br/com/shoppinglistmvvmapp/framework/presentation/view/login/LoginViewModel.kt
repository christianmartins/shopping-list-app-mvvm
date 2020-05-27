package br.com.shoppinglistmvvmapp.framework.presentation.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.shoppinglistmvvmapp.domain.usecase.LoginUseCase
import br.com.shoppinglistmvvmapp.framework.presentation.view.common.AbstractViewModel
import br.com.shoppinglistmvvmapp.framework.presentation.view.login.state.LoginViewState
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.safeRunOnUiThread
import br.com.shoppinglistmvvmapp.utils.GlobalUtils
import br.com.shoppinglistmvvmapp.utils.LoggedUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
): AbstractViewModel() {

    private val _loginViewState = MutableLiveData<LoginViewState>()
    val loginViewState: LiveData<LoginViewState> = _loginViewState

    fun login(email: String, password: String){
        var isSuccess = false
        stateLoading()
        viewModelScope.launch(Dispatchers.IO) {
            isSuccess = loginUseCase.execute(email, password)
        }.invokeOnCompletion {
            safeRunOnUiThread {
                _loginViewState.value = if(isSuccess){
                    clearData()
                    LoginViewState.Success
                }
                else
                    LoginViewState.Error
            }
        }
    }

    private fun stateLoading(){
        _loginViewState.value = LoginViewState.Loading
    }

    fun onVisitorClick(){
        clearLoggedUser()
        _loginViewState.value = LoginViewState.ToVisitor
    }

    fun onRegisterClick(){
        clearData()
        _loginViewState.value = LoginViewState.ToUserRegister
    }

    private fun clearLoggedUser(){
        LoggedUser.clear()
    }

    private fun clearData(){
        GlobalUtils.clearLists()
    }

    fun clearState(){
        _loginViewState.value = LoginViewState.None
    }
}