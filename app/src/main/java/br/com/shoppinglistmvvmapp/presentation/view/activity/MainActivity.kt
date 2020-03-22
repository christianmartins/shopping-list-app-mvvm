package br.com.shoppinglistmvvmapp.presentation.view.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.extensions.setupBottomNavigationBar
import br.com.shoppinglistmvvmapp.presentation.view.fragment.ShoppingListFragmentMVVMDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(){

    private var currentNavController: LiveData<NavController>? = null

    val fab by lazy {
        findViewById<FloatingActionButton?>(R.id.fab)
    }

    val bottomNavigationMenu by lazy {
        findViewById<BottomNavigationView?>(R.id.bottom_app_bar)
    }

    private val permissionRecordAudioCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideActionBar()

        if (savedInstanceState == null) {
            currentNavController = setupBottomNavigationBar()
        }

        onBottomNavigationMenuItemReselect()
        tempRedirectToMVVMFragment()
    }

    private fun tempRedirectToMVVMFragment(){
        fab_redirect_to_mvvm?.setOnClickListener {
            findNavController(R.id.nav_host_container).navigate(ShoppingListFragmentMVVMDirections.actionGlobalShoppingListFragmentMVVM())
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        currentNavController = setupBottomNavigationBar()
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp()?: false
    }

    private fun onBottomNavigationMenuItemReselect(){
        bottom_app_bar.setOnNavigationItemReselectedListener { menuItem ->
            findNavController(R.id.nav_host_container).navigate(menuItem.itemId)
        }
    }

    fun checkAudioRecordPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), permissionRecordAudioCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            permissionRecordAudioCode -> {
                if (grantResults.isNotEmpty().not() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, getString(R.string.request_permission_record_audio_failed), Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }
}

