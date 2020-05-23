package br.com.shoppinglistmvvmapp.framework.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class AbstractWithDataBindingFragment<Binding: ViewDataBinding>(
    @LayoutRes private val layoutId: Int
): AbstractSpeakAndRecognitionFragment() {

    protected lateinit var binding: Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initBinding(inflater, container)
        return binding.root
    }

    private fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): Binding {
        return DataBindingUtil.inflate(
            inflater,
            layoutId,
            container,
            false
        )
    }

    abstract fun initBindingProperties()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindingProperties()
    }
}