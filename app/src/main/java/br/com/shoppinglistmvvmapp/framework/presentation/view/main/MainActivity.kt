package br.com.shoppinglistmvvmapp.framework.presentation.view.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import br.com.shoppinglistmvvmapp.R
import br.com.shoppinglistmvvmapp.databinding.ActivityMainBinding
import br.com.shoppinglistmvvmapp.framework.presentation.view.common.AbstractActivity
import br.com.shoppinglistmvvmapp.framework.presentation.view.util.extension.setupBottomNavigationBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AbstractActivity(){

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }

    private var currentNavController: LiveData<NavController>? = null

    val fab by lazy {
        binding.fab
    }

    val bottomNavigationMenu by lazy {
        binding.bottomAppBar
    }

    private val permissionRecordAudioCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        hideActionBar()

        if (savedInstanceState == null) {
            currentNavController = setupBottomNavigationBar()
        }

        onBottomNavigationMenuItemReselect()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        currentNavController = setupBottomNavigationBar()
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp()?: false
    }

    private fun onBottomNavigationMenuItemReselect(){
        binding.bottomAppBar.setOnNavigationItemReselectedListener { menuItem ->
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

