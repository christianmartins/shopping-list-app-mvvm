package br.com.shoppinglistmvvmapp.framework.presentation.view.common.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

abstract class AbstractCollectionMVVMFragment<Binding: ViewDataBinding>(
    @LayoutRes private val layoutId: Int
): AbstractWithDataBindingFragment<Binding>(
    layoutId
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFabAndBottomNav()
        myFabOnClickListener()
        initAdapter()
    }

    private fun myFabOnClickListener(){
        getFab()?.setOnClickListener {
            onClickFloatingButton()
        }
    }

    abstract fun onClickFloatingButton ()
    abstract fun initAdapter()

}