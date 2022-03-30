package com.io.gazette

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.io.gazette.data.api.NytApi
import com.io.gazette.data.api.buildNytAPi
import com.io.gazette.data.api.mappers.toDomainNewsItem
import com.io.gazette.databinding.ActivityMainBinding
import github.com.st235.lib_expandablebottombar.navigation.ExpandableBottomBarNavigationUI
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {


    private var _binding:ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        ExpandableBottomBarNavigationUI.setupWithNavController(binding.bottomNavBar, navController)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}