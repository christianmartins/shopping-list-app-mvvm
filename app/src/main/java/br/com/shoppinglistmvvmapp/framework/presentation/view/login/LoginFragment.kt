package br.com.shoppinglistmvvmapp.framework.presentation.view.login

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.databinding.LoginFragmentLayoutBinding
import br.com.shoppinglistmvvmapp.framework.presentation.view.common.fragment.AbstractDataBindingFragment
import br.com.shoppinglistmvvmapp.framework.presentation.view.shoppinglist.ShoppingListFragmentDirections
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.getSafeText
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.safeRunOnUiThread
import br.com.shoppinglistmvvmapp.utils.GlobalUtils
import br.com.shoppinglistmvvmapp.utils.LoggedUser
import br.com.shoppinglistmvvmapp.utils.extension.nonNullable
import kotlinx.android.synthetic.main.login_fragment_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment: AbstractDataBindingFragment<LoginFragmentLayoutBinding>(R.layout.login_fragment_layout) {

    private val presenter by lazy {
        LoginPresenter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        hideFabAndBottomNav()
        super.onViewCreated(view, savedInstanceState)
        visitorEnterClick()
        login()
        setPreferenceData()
        onClickButtonUserRegister()
    }

    private fun login(){
        login_enter.setOnClickListener {
            context?.let {
                val alertDialog = showProgressBarDialog(it)
                val email = getEmail()
                val password = getPassword()

                lifecycleScope.launch(Dispatchers.IO) {
                    val isSuccess = presenter.loginAsync(email, password)
                    onFinishLoginRequest(isSuccess)
                    activity?.runOnUiThread { alertDialog.hide() }
                }
            }
        }
    }

    private fun getEmail(): String{
        return login_edit_text_email?.getSafeText().nonNullable().trim()
    }

    private fun getPassword(): String{
        return  login_edit_text_passowrd?.getSafeText().nonNullable().trim()
    }

    private fun onFinishLoginRequest(isSuccess: Boolean){
        if(isSuccess){
            savePreferenceData()
            navigateToShoppingListFragment()
        }else{
            showMessage(R.string.generic_dialog_title, R.string.login_error)
        }
    }

    private fun visitorEnterClick(){
        visitor_enter.setOnClickListener {
            LoggedUser.clear()
            navigateToShoppingListFragment()
        }
    }

    private fun setPreferenceData(){
        with(activity?.getSharedPreferences("login", Context.MODE_PRIVATE)) {
            login_edit_text_email?.setText(this?.getString("email", "").nonNullable())
            login_edit_text_passowrd?.setText(this?.getString("clearTextPassword", "").nonNullable())
        }
    }

    private fun savePreferenceData(){
        activity?.getSharedPreferences("login", Context.MODE_PRIVATE).run {
            this?.edit {
                putString("email", getEmail())
                putString("clearTextPassword", getPassword())
                apply()
            }
        }
    }

    private fun navigateToShoppingListFragment(){
        safeRunOnUiThread {
            GlobalUtils.clearLists()
            findNavController().navigate(ShoppingListFragmentDirections.actionGlobalShoppingListFragment())
        }
    }

    private fun onClickButtonUserRegister(){
        login_redirect_to_user_register?.setOnClickListener {
            navigateToUserRegister()
        }
    }

    private fun navigateToUserRegister(){
        safeRunOnUiThread {
            GlobalUtils.clearLists()
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToUserRegistrationView())
        }
    }

    override fun initBindingProperties() {
        TODO("Not yet implemented")
    }
}