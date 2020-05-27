package br.com.shoppinglistmvvmapp.di.module

import android.content.Context
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.ViewUtil
import org.koin.dsl.module
import java.lang.ref.WeakReference

val viewUtilModule = module {
    factory { (weakContext: WeakReference<Context?>) -> provideViewUtil(weakContext)}
}

private fun provideViewUtil(weakContext: WeakReference<Context?>): ViewUtil{
    return ViewUtil(weakContext)
}