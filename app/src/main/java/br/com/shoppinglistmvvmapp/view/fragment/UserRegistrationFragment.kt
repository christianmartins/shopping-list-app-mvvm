package br.com.shoppinglistmvvmapp.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.extensions.*
import br.com.shoppinglistmvvmapp.presenter.UserRegistrationPresenter
import kotlinx.android.synthetic.main.user_registration_view_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRegistrationFragment : BaseFragment() {

    private val presenter by lazy {
        UserRegistrationPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideFabAndBottomNav()
        return inflater.inflate(R.layout.user_registration_view_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onRegisterButtonClick()
    }

    private fun onRegisterButtonClick() {
        user_registration_view_register.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser(){
        if(validateFields()){
            context?.let {
                val alertDialog = showProgressBarDialog(it)

                lifecycleScope.launch(Dispatchers.IO) {
                    val isSuccess = presenter.registerUser(getEmail(), getPassword(), getFirstName(), getLastName())
                    activity?.runOnUiThread { alertDialog.hide() }

                    if(isSuccess) {
                        showMessage(R.string.generic_dialog_title, R.string.user_registration_view_register_is_success, onOkClick = {
                            activity?.runOnUiThread {
                                onSuccess(it)
                            }
                        })
                    }else{
                        showMessage(R.string.generic_dialog_title, R.string.user_registration_view_register_not_is_success)
                    }
                }
            }
        }
    }

    private fun onSuccess(context: Context){
        val alertDialog= showProgressBarDialog(context)

        lifecycleScope.launch(Dispatchers.IO) {
            val isSuccess = presenter.loginAsync(getEmail(), getPassword())
            if(isSuccess){
                navigateToShoppingList()
            }else{
                findNavController().navigateUp()
            }
            activity?.runOnUiThread {alertDialog.hide()}
        }
    }

    private fun validateFields(): Boolean{
        return user_registration_view_text_input_email.isValidEmail(getEmail())
        && user_registration_view_text_input_password.isValidPassword(getPassword())
        && user_registration_view_text_input_password_repeat.isValidPasswordRepeat(getPasswordRepeat())
        && user_registration_view_text_input_firstName.isValidFirstName(getFirstName())
        && user_registration_view_text_input_last_name.isValidLastName(getLastName())
    }

    private fun getEmail(): String{
        return user_registration_view_edit_text_email.getSafeTextWithTrim()
    }

    private fun getPassword(): String{
        return user_registration_view_edit_text_passowrd.getSafeTextWithTrim()
    }

    private fun getPasswordRepeat(): String{
        return user_registration_view_edit_text_passowrd_repeat.getSafeTextWithTrim()
    }

    private fun getFirstName(): String{
        return user_registration_view_edit_text_first_name.getSafeTextWithTrim()
    }

    private fun getLastName(): String{
        return user_registration_view_edit_text_last_name.getSafeTextWithTrim()
    }

    private fun navigateToShoppingList(){
        activity?.runOnUiThread {
            findNavController().navigate(ShoppingListFragmentDirections.actionGlobalShoppingListFragment())
        }
    }
}