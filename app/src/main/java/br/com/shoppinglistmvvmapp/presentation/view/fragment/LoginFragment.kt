package br.com.shoppinglistmvvmapp.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.extensions.getSafeText
import br.com.shoppinglistmvvmapp.extensions.nonNullable
import br.com.shoppinglistmvvmapp.presenter.LoginPresenter
import br.com.shoppinglistmvvmapp.utils.GlobalUtils
import br.com.shoppinglistmvvmapp.utils.LoggedUser
import kotlinx.android.synthetic.main.login_fragment_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment: BaseFragment() {

    private val presenter by lazy {
        LoginPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        hideFabAndBottomNav()
        return inflater.inflate(R.layout.login_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        activity?.runOnUiThread {
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
        activity?.runOnUiThread {
            GlobalUtils.clearLists()
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToUserRegistrationView())
        }
    }
}