package br.com.shoppinglistmvvmapp.framework.presentation.view.login

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.databinding.LoginFragmentLayoutBinding
import br.com.shoppinglistmvvmapp.framework.presentation.view.common.fragment.AbstractDataBindingFragment
import br.com.shoppinglistmvvmapp.framework.presentation.view.login.state.LoginViewState
import br.com.shoppinglistmvvmapp.framework.presentation.view.shoppinglist.ShoppingListFragmentDirections
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.getSafeTextWithTrim
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.safeRunOnUiThread
import br.com.shoppinglistmvvmapp.utils.extension.nonNullable
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment: AbstractDataBindingFragment<LoginFragmentLayoutBinding>(R.layout.login_fragment_layout) {

    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        hideFabAndBottomNav()
        super.onViewCreated(view, savedInstanceState)
        setPreferenceData()
        onLoginClick()
        onObserverLoginState()
    }

    private fun onObserverLoginState(){
        viewModel.loginViewState.observe(viewLifecycleOwner, Observer {state ->
            with(binding){
                progress.isVisible = false
                when(state){
                    is LoginViewState.Loading -> {
                        progress.isVisible = true
                    }
                    is LoginViewState.Success -> {
                        savePreferenceData()
                        navigateToShoppingListFragment()
                    }
                    is LoginViewState.Error -> {
                        viewUtil.showMessage(R.string.generic_dialog_title, R.string.login_error)
                    }
                    is LoginViewState.ToVisitor ->  {
                        navigateToShoppingListFragment()
                    }
                    is LoginViewState.ToUserRegister -> {
                        navigateToUserRegister()
                    }
                    is LoginViewState.None -> {}
                }
            }
        })
    }

    private fun onLoginClick(){
        binding.loginEnter.setOnClickListener {
            viewModel.login(getEmail(), getPassword())
        }
    }

    private fun setPreferenceData(){
        with(context?.getSharedPreferences("login", Context.MODE_PRIVATE)) {
            binding.loginEditTextEmail.setText(this?.getString("email", "").nonNullable())
            binding.loginEditTextPassowrd.setText(this?.getString("clearTextPassword", "").nonNullable())
        }
    }

    private fun savePreferenceData(){
        context?.getSharedPreferences("login", Context.MODE_PRIVATE).run {
            this?.edit {
                putString("email", getEmail())
                putString("clearTextPassword", getPassword())
                apply()
            }
        }
    }

    private fun navigateToShoppingListFragment(){
        safeRunOnUiThread {
            findNavController().navigate(ShoppingListFragmentDirections.actionGlobalShoppingListFragment())
            viewModel.clearState()
        }
    }

    private fun navigateToUserRegister(){
        safeRunOnUiThread {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToUserRegistrationView())
            viewModel.clearState()
        }
    }

    private fun getEmail(): String{
        return binding.loginEditTextEmail.getSafeTextWithTrim()
    }

    private fun getPassword(): String{
        return binding.loginEditTextPassowrd.getSafeTextWithTrim()
    }

    override fun initBindingProperties() {
        binding.vm = viewModel
    }
}