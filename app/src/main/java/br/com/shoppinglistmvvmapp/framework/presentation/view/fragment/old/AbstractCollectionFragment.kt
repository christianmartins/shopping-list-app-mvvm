package br.com.shoppinglistmvvmapp.framework.presentation.view.fragment.old

import android.os.Bundle
import android.view.View
import br.com.shoppinglistmvvmapp.framework.presentation.view.fragment.AbstractSpeakAndRecognitionFragment

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