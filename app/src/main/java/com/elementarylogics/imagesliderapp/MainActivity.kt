package com.elementarylogics.imagesliderapp


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.elementarylogics.imagesliderapp.activities.MobileRegisterationActivity
import com.elementarylogics.imagesliderapp.activities.ProfileActivity
import com.elementarylogics.imagesliderapp.fragments.MyOrdersSlidersFragments
import com.elementarylogics.imagesliderapp.fragments.OffersFragment
import com.elementarylogics.imagesliderapp.fragments.OffersSliderFragment
import com.elementarylogics.imagesliderapp.fragments.profile.ProfileSliderFragment
import com.elementarylogics.imagesliderapp.utils.SharedPreference
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


//        fragmentManager.beginTransaction()
//            .add(R.id.mainNavigationFragment, profileSliderFragment, "3")
//            .hide(profileSliderFragment).commit()
//        fragmentManager.beginTransaction()
//            .add(R.id.mainNavigationFragment, ordersSlidersFragments, "2")
//            .hide(ordersSlidersFragments).commit()
//        fragmentManager.beginTransaction()
//            .add(R.id.mainNavigationFragment, offersFragment, "1")
//            .hide(offersFragment).commit()
        fragmentManager.beginTransaction()
            .add(R.id.container, dashboradSliderFragment, "0").commit();

//        onDashboardFragmetnSelected()
        containerFrame = findViewById(R.id.container)
        containerFrame.visibility = View.VISIBLE
        mainNavigationFragment.visibility = View.INVISIBLE


        changeStatusBarColor()

        activeFragment = dashboradSliderFragment
        selectedFragment = dashboradSliderFragment

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelected)
    }

    val navigationItemSelected = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
//            when (item.itemId) {
//                R.id.dashboardFrag -> {
//                    supportFragmentManager.beginTransaction().hide(activeFragment)
//                        .show(dashboradSliderFragment).commit();
//                    activeFragment = dashboradSliderFragment
//
//                    return true
//                }
//                R.id.offersFrag -> {
//                    supportFragmentManager.beginTransaction().hide(activeFragment)
//                        .show(offersFragment).commit();
//                    activeFragment = offersFragment
//                    offersFragment.Update()
//                    return true
//                }
//                R.id.myOrdersFrag -> {
//                    supportFragmentManager.beginTransaction().hide(activeFragment)
//                        .show(ordersSlidersFragments).commit();
//                    activeFragment = ordersSlidersFragments
//                    return true
//                }
//                R.id.profileFrag -> {
//
//                    checkProfile()
//                    return true
//                }
//            }

//            changeFragments(item)

            when (item.itemId) {
                R.id.dashboardFrag -> {
                    containerFrame.visibility = View.VISIBLE
                    mainNavigationFragment.visibility = View.INVISIBLE

                }
                R.id.myOrdersFrag -> {

                    selectedFragment = MyOrdersSlidersFragments.newInstance("", "")
                    setFragments()
                }
                R.id.offersFrag -> {

                    selectedFragment = OffersSliderFragment.newInstance("", "")
                    setFragments()
                }
                R.id.profileFrag -> {
                    if (checkProfile()) {
                        selectedFragment = ProfileSliderFragment.newInstance("", "")
                        setFragments()
                    } else {
                        return false
                    }


                }
            }

            return true
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        selectedFragment.onActivityResult(requestCode, resultCode, data)
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

    fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.colorPrimary
                )
            )

        }
    }

    lateinit var selectedFragment: Fragment
    lateinit var containerFrame: FrameLayout
    fun changeFragments(item: MenuItem) {


        when (item.itemId) {
            R.id.dashboardFrag -> {
                containerFrame.visibility = View.VISIBLE
                mainNavigationFragment.visibility = View.INVISIBLE

                return
            }
            R.id.myOrdersFrag -> {

                selectedFragment = MyOrdersSlidersFragments.newInstance("", "")
                setFragments()
            }
            R.id.offersFrag -> {

                selectedFragment = OffersSliderFragment.newInstance("", "")
                setFragments()
            }
            R.id.profileFrag -> {
                if (checkProfile()) {
                    selectedFragment = ProfileSliderFragment.newInstance("", "")
                    setFragments()
                }


            }
        }


    }

    fun setFragments() {
        supportFragmentManager.beginTransaction().replace(
            R.id.mainNavigationFragment,
            selectedFragment
        ).commit()
        containerFrame.visibility = View.INVISIBLE
        mainNavigationFragment.visibility = View.VISIBLE
    }


    fun checkProfile(): Boolean {
        val user = SharedPreference.getUserData(this)
        if (user != null) {
            if (user.is_complete_profile == 1) {
                return true
            } else {
                if (user.is_register_number == 1) {
                    startActivity(Intent(this, ProfileActivity::class.java).putExtra("from_main",true))
                } else {
                    startActivity(Intent(this, MobileRegisterationActivity::class.java).putExtra("from_main",true))
                }
                return false
            }
        } else {
            startActivity(Intent(this, MobileRegisterationActivity::class.java).putExtra("from_main",true))
            return false
        }
    }

}
