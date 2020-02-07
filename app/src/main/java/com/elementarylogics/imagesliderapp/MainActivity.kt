package com.elementarylogics.imagesliderapp

//import androidx.navigation.Navigation
//import androidx.navigation.ui.NavigationUI
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    private fun setupNavigation() {

        val navController = Navigation.findNavController(this, R.id.mainNavigationFragment)
//        setupActionBarWithNavController(this, navController)
        navController.addOnNavigatedListener { controller, destination ->
            controller.popBackStack(
                destination.id,
                false
            )
        }
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }


}
