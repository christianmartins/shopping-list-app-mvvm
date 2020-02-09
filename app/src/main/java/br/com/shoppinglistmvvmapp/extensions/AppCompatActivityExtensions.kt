package br.com.shoppinglistmvvmapp.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.shoppinglistmvvmapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView


/**
 * Called on first creation and when restoring state.
 */
fun AppCompatActivity.setupBottomNavigationBar() : LiveData<NavController> {
    val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_app_bar)

    val navGraphIds = listOf(R.navigation.main_nav_graph)

    // Setup the bottom navigation view with a list of navigation graphs
    val controller = bottomNavigationView.setupWithNavController(
        navGraphIds = navGraphIds,
        fragmentManager = this.supportFragmentManager,
        containerId = R.id.nav_host_container,
        intent = intent
    )

    // Whenever the selected controller changes, setup the action bar.
    controller.observe(this, Observer { navController ->
        setupActionBarWithNavController(navController)
    })
    return controller
}
