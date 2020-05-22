package br.com.shoppinglistmvvmapp.framework.presentation.view.fragment

import android.os.Bundle
import android.view.View

abstract class AbstractCollectionFragment: AbstractSpeakAndRecognitionFragment() {

    abstract fun initAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFabAndBottomNav()
        myFabOnClickListener()
    }

    private fun myFabOnClickListener(){
        getFab()?.setOnClickListener {
            onClickFloatingButton()
        }
    }

    abstract fun onClickFloatingButton ()
}