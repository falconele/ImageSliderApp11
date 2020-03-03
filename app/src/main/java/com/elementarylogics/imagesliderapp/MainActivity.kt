package com.elementarylogics.imagesliderapp


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.elementarylogics.imagesliderapp.profile.ProfileSliderFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    //    var bottomNavigation: BottomNavigationView? = null


    lateinit var profileSliderFragment: ProfileSliderFragment
    lateinit var offersFragment: OffersFragment
    lateinit var ordersSlidersFragments: MyOrdersSlidersFragments
    lateinit var dashboradSliderFragment: DashboradSliderFragment
    val fragmentManager = supportFragmentManager
    lateinit var activeFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        profileSliderFragment = ProfileSliderFragment.newInstance("", "")
        offersFragment = OffersFragment.newInstance("", "")
        ordersSlidersFragments = MyOrdersSlidersFragments.newInstance("", "")
        dashboradSliderFragment = DashboradSliderFragment.newInstance("", "")


        fragmentManager.beginTransaction()
            .add(R.id.mainNavigationFragment, profileSliderFragment, "3")
            .hide(profileSliderFragment).commit()
        fragmentManager.beginTransaction()
            .add(R.id.mainNavigationFragment, ordersSlidersFragments, "2")
            .hide(ordersSlidersFragments).commit()
        fragmentManager.beginTransaction()
            .add(R.id.mainNavigationFragment, offersFragment, "1")
            .hide(offersFragment).commit()
        fragmentManager.beginTransaction()
            .add(R.id.mainNavigationFragment, dashboradSliderFragment, "0").commit();

//        onDashboardFragmetnSelected()


        changeStatusBarColor()

        activeFragment = dashboradSliderFragment

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelected)
    }

    val navigationItemSelected = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.dashboardFrag -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(dashboradSliderFragment).commit();
                    activeFragment = dashboradSliderFragment

                    return true
                }
                R.id.offersFrag -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(offersFragment).commit();
                    activeFragment = offersFragment
                    offersFragment.Update()
                    return true
                }
                R.id.myOrdersFrag -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(ordersSlidersFragments).commit();
                    activeFragment = ordersSlidersFragments
                    return true
                }
                R.id.profileFrag -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment)
                        .show(profileSliderFragment).commit();
                    activeFragment = profileSliderFragment
                    profileSliderFragment.updateMethod()
                    return true
                }
            }
            return true
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activeFragment.onActivityResult(requestCode, resultCode, data)
    }

//    fun openFragment(fragment: Fragment) {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.mainNavigationFragment, fragment)
//        transaction.addToBackStack(null)
//        transaction.commit()
//    }

    private val BACK_STACK_ROOT_TAG = "root_fragment"

//    fun onDashboardFragmetnSelected() {
//        // Pop off everything up to and including the current tab
//        val fragmentManager = supportFragmentManager
//        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//
//        // Add the new tab fragment
//        fragmentManager.beginTransaction()
//            .replace(R.id.mainNavigationFragment, dashboradSliderFragment)
//            .addToBackStack(BACK_STACK_ROOT_TAG)
//            .commit()
//    }

    override fun onBackPressed() {
        if (activeFragment != dashboradSliderFragment) {
            bottomNavigationView.selectedItemId = R.id.dashboardFrag
            supportFragmentManager.beginTransaction().hide(activeFragment)
                .show(dashboradSliderFragment).commit();
            activeFragment = dashboradSliderFragment
        } else {
            super.onBackPressed()
        }
    }

    fun changeStatusBarColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))

        }
    }
}
