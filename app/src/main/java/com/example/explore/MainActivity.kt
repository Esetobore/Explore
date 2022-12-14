package com.example.explore

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.explore.databinding.ActivityMainBinding
import com.example.explore.viewmodel.ExploreViewModel
import com.example.explore.viewmodel.ViewModelProviderFactory
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: ExploreViewModel
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModelProviderFactory = ViewModelProviderFactory(application)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(ExploreViewModel::class.java)

        val fragHost = supportFragmentManager.findFragmentById(R.id.fragHost) as NavHostFragment?
        if (fragHost != null) {
            navController = fragHost.findNavController()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }




}
