package br.com.shoppinglistmvvmapp.framework.presentation.view.common.fragment

import androidx.fragment.app.Fragment
import br.com.shoppinglistmvvmapp.framework.presentation.view.main.MainActivity
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.ViewUtil
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.hide
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.show
import br.com.shoppinglistmvvmapp.utils.GlobalUtils
import br.com.shoppinglistmvvmapp.utils.event.MessageEvent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.lang.ref.WeakReference


open class AbstractFragment: Fragment(){

    protected val viewUtil: ViewUtil by inject{
        parametersOf(WeakReference(context))
    }

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

}