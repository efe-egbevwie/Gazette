package com.io.gazette

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.io.gazette.databinding.ActivityMainBinding
import com.io.gazette.utils.gone
import com.io.gazette.utils.invisible
import com.io.gazette.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import github.com.st235.lib_expandablebottombar.navigation.ExpandableBottomBarNavigationUI

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        ExpandableBottomBarNavigationUI.setupWithNavController(binding.bottomNavBar, navController)

        onNavDestinationChanged(navController)
    }

    private fun onNavDestinationChanged(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment || destination.id == R.id.readLaterCollectionsFragment) showBottomNav() else hideBottomBar()

        }
    }

    fun showBottomNav() {
        if (binding.bottomNavBar.isVisible) return
        TransitionManager.beginDelayedTransition(binding.bottomNavBar, Slide())
        binding.bottomNavBar.visible()
    }

    fun hideBottomBar() {
        TransitionManager.beginDelayedTransition(binding.bottomNavBar, Slide())
        binding.bottomNavBar.gone()
    }


}