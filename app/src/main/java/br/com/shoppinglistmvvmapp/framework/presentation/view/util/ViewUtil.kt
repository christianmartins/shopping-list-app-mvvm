package br.com.shoppinglistmvvmapp.framework.presentation.view.util

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.customview.CustomTextInputLayout
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.safeRunOnUiThread
import br.com.shoppinglistmvvmapp.utils.extension.nonNullable
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.ref.WeakReference

class ViewUtil(
    private val weakContext: WeakReference<Context?>
) {

    fun showMessage(
        @StringRes resStringTitle: Int,
        @StringRes resStringMessage: Int,
        @StringRes resStringPositiveButton: Int = R.string.ok_confirm,
        onOkClick:(() -> Unit)? = null
    ){
        safeRunOnUiThread {
            weakContext.get()?.let {
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
        weakContext.get()?.let {
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
        text: String = weakContext.get()?.getString(R.string.nothing).nonNullable(),
        @StringRes resStringPositiveButton: Int = R.string.ok_confirm,
        @StringRes resStringNegativeButton: Int = R.string.close_confirm,
        onYesClick:((newValue: String) -> Unit)? = null,
        onNoClick:(() -> Unit)? = null
    ){
        weakContext.get()?.let { contextNonNullable->
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
            setHint(context.getString(resStringHint))
            setText(textArgs)
        }
    }


    fun showProgressBarDialog(context: Context): AlertDialog {
        return MaterialAlertDialogBuilder(context)
            .setBackground(ColorDrawable(Color.TRANSPARENT))
            .setView(R.layout._progress_bar)
            .setCancelable(false)
            .show()
    }
}