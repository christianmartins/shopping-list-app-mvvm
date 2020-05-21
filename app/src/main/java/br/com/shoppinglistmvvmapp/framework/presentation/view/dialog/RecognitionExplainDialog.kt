package br.com.shoppinglistmvvmapp.framework.presentation.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.annotation.LayoutRes

class RecognitionExplainDialog(context: Context, @LayoutRes resLayout: Int): Dialog(context) {

    private val dialog = View.inflate(context, resLayout, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setCancelable(false)
        setTitle(null)
        setContentView(dialog)
    }
}