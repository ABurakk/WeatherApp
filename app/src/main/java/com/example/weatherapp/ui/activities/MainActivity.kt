package com.example.weatherapp.ui.activities

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity(),EasyPermissions.PermissionCallbacks  {


    private lateinit var navController: NavController
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this,R.id.fragment_container)

        binding.bottomNavigationView.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this,navController)

        if(!checkLocationPermisson()) requestLocationPermission()

    }



    private fun checkLocationPermisson() = EasyPermissions.
    hasPermissions(this,android.Manifest.permission.ACCESS_FINE_LOCATION)


    private fun requestLocationPermission() {
        EasyPermissions.requestPermissions(
            this,
            "This application cannot work without Location Permission.",
            1,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        Toast.makeText(this,"İzin vermeden kullanamazsınız",Toast.LENGTH_SHORT).show()
        System.exit(-1)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(
            this,
            "Permission Granted!",
            Toast.LENGTH_SHORT
        ).show()
    }

}