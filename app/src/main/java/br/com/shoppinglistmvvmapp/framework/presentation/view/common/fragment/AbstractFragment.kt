package br.com.shoppinglistmvvmapp.framework.presentation.view.common.fragment

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.hide
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.show
import br.com.shoppinglistmvvmapp.utils.GlobalUtils
import br.com.shoppinglistmvvmapp.utils.event.MessageEvent
import br.com.shoppinglistmvvmapp.framework.presentation.view.main.MainActivity
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.customview.CustomTextInputLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference


open class AbstractFragment: Fragment(){

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(event: MessageEvent) {}

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        GlobalUtils.fragmentAlive = this.javaClass.name
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    fun getWeakMainActivity(): WeakReference<MainActivity?> {
        return WeakReference(activity as? MainActivity)
    }

    fun getFab(): FloatingActionButton?{
        return getWeakMainActivity().get()?.fab
    }

    private fun getBottomNavigationMenuView(): BottomNavigationView?{
        return getWeakMainActivity().get()?.bottomNavigationMenu
    }

    fun hideFabAndBottomNav(){
        getFab()?.hide()
        getBottomNavigationMenuView()?.hide()
    }

    fun showFabAndBottomNav(){
        getFab()?.show()
        getBottomNavigationMenuView()?.show()
    }

    fun showMessage(
        @StringRes resStringTitle: Int,
        @StringRes resStringMessage: Int,
        @StringRes resStringPositiveButton: Int = R.string.ok_confirm,
        onOkClick:(() -> Unit)? = null
    ){
        activity?.runOnUiThread {
            context?.let {
                confirmMessage(
                    context = it,
                    resStringTitle = resStringTitle,
                    resStringMessage = resStringMessage,
                    resStringPositiveButton = resStringPositiveButton,
                    resStringNegativeButton = R.string.nothing,
                    positiveClickListener = DialogInterface.OnClickListener { dialogInterface, _ ->
                        onOkClick?.invoke()
                        dialogInterface.dismiss()
                    },
                    negativeClickListener = null
                )
            }
        }
    }

    fun yesConfirmMessage(
        @StringRes resStringTitle: Int = R.string.generic_dialog_title,
        @StringRes resStringMessage: Int,
        onYesClick:(() -> Unit)? = null,
        onNoClick:(() -> Unit)? = null
    ){
        context?.let {
            confirmMessage(
                context = it,
                resStringTitle = resStringTitle,
                resStringMessage = resStringMessage,
                resStringPositiveButton = R.string.yes_confirm,
                resStringNegativeButton = R.string.no_confirm,
                positiveClickListener = DialogInterface.OnClickListener { dialogInterface, _ ->
                    onYesClick?.invoke()
                    dialogInterface.dismiss()
                },
                negativeClickListener =  DialogInterface.OnClickListener { dialogInterface, _ ->
                    onNoClick?.invoke()
                    dialogInterface.dismiss()
                }
            )
        }
    }

    private fun confirmMessage(
        context: Context,
        @StringRes resStringTitle: Int,
        @StringRes resStringMessage: Int,
        @StringRes resStringPositiveButton: Int,
        @StringRes resStringNegativeButton: Int,
        positiveClickListener: DialogInterface.OnClickListener,
        negativeClickListener: DialogInterface.OnClickListener?
    ){
        MaterialAlertDialogBuilder(context)
        .setTitle(resStringTitle)
        .setMessage(resStringMessage)
        .setPositiveButton(resStringPositiveButton, positiveClickListener).apply {
            if(negativeClickListener != null)
                setNegativeButton(resStringNegativeButton, negativeClickListener)
        }.show()
    }

    fun editMessage(
        @StringRes resStringTitle: Int,
        @StringRes resStringHint: Int,
        text: String = getString(R.string.nothing),
        @StringRes resStringPositiveButton: Int = R.string.ok_confirm,
        @StringRes resStringNegativeButton: Int = R.string.close_confirm,
        onYesClick:((newValue: String) -> Unit)? = null,
        onNoClick:(() -> Unit)? = null
    ){
        context?.let {contextNonNullable->
            val editText = createCustomTextInputLayout(contextNonNullable, resStringHint, text)

            MaterialAlertDialogBuilder(contextNonNullable)
                .setTitle(resStringTitle)
                .setView(editText)
                .setPositiveButton(resStringPositiveButton) { dialogInterface, _ ->
                    onYesClick?.invoke(editText.getText())
                    dialogInterface.dismiss()
                }
                .setNegativeButton(resStringNegativeButton) { dialogInterface, _ ->
                    onNoClick?.invoke()
                    dialogInterface.dismiss()
                }.show()
        }
    }

    private fun createCustomTextInputLayout(context: Context, @StringRes resStringHint: Int, textArgs: String): CustomTextInputLayout {
        return CustomTextInputLayout(
            context
        ).apply {
            setHint(getString(resStringHint))
            setText(textArgs)
        }
    }


    fun showProgressBarDialog(context: Context): AlertDialog{
        return MaterialAlertDialogBuilder(context)
        .setBackground(ColorDrawable(Color.TRANSPARENT))
        .setView(R.layout._progress_bar)
        .setCancelable(false)
        .show()
    }
}