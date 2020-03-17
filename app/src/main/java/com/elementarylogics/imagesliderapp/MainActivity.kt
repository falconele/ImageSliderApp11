package com.elementarylogics.imagesliderapp


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.elementarylogics.imagesliderapp.activities.MobileRegisterationActivity
import com.elementarylogics.imagesliderapp.activities.ProfileActivity
import com.elementarylogics.imagesliderapp.fragments.MyOrdersSlidersFragments
import com.elementarylogics.imagesliderapp.fragments.OffersFragment
import com.elementarylogics.imagesliderapp.fragments.OffersSliderFragment
import com.elementarylogics.imagesliderapp.fragments.profile.ProfileSliderFragment
import com.elementarylogics.imagesliderapp.utils.ApplicationUtils
import com.elementarylogics.imagesliderapp.utils.SharedPreference
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenuView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //    var bottomNavigation: BottomNavigationView? = null


    lateinit var profileSliderFragment: ProfileSliderFragment
    lateinit var offersFragment: OffersFragment
    lateinit var ordersSlidersFragments: MyOrdersSlidersFragments
    lateinit var dashboradSliderFragment: DashboradSliderFragment
    val fragmentManager = supportFragmentManager
    lateinit var activeFragment: Fragment


    /// navigation drawer
    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)

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

        containerFrame = findViewById(R.id.container)
        containerFrame.visibility = View.VISIBLE
        mainNavigationFragment.visibility = View.INVISIBLE


        changeStatusBarColor()

        activeFragment = dashboradSliderFragment
        selectedFragment = dashboradSliderFragment

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelected)


//        bottomNavigationView.selectedItemId= R.id.offersFrag

        //navigation drawer
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        supportActionBar?.setHomeButtonEnabled(false)
        disableNavigationViewScrollbars(navView)
        initializeMenuItems()


    }

    lateinit var login_nav: MenuItem
    lateinit var update_profile_nav: MenuItem
    lateinit var nav_my_addresses: MenuItem
    lateinit var nav_my_cart: MenuItem
    lateinit var nav_my_orders: MenuItem
    lateinit var nav_language: MenuItem
    lateinit var nav_help: MenuItem
    lateinit var nav_rate_us: MenuItem
    lateinit var nav_share: MenuItem
    lateinit var nav_terms_and_condition: MenuItem
    lateinit var nav_privacy_policy: MenuItem
    lateinit var nav_contact_us: MenuItem
    lateinit var nav_about_us: MenuItem
    lateinit var nav_app_release: MenuItem
    lateinit var nav_signout: MenuItem


    fun initializeMenuItems() {
        val menuFromNAv = navView.menu
        login_nav = menuFromNAv.findItem(R.id.nav_login)
        update_profile_nav = menuFromNAv.findItem(R.id.nav_update_profile)
        nav_my_addresses = menuFromNAv.findItem(R.id.nav_my_addresses)
        nav_my_cart = menuFromNAv.findItem(R.id.nav_my_cart)
        nav_my_orders = menuFromNAv.findItem(R.id.nav_my_orders)
        nav_language = menuFromNAv.findItem(R.id.nav_change_language)
        nav_help = menuFromNAv.findItem(R.id.nav_help)
        nav_rate_us = menuFromNAv.findItem(R.id.nav_rate_us)
        nav_share = menuFromNAv.findItem(R.id.nav_share)
        nav_terms_and_condition = menuFromNAv.findItem(R.id.nav_terms_conditions)
        nav_privacy_policy = menuFromNAv.findItem(R.id.nav_privacy_policy)
        nav_contact_us = menuFromNAv.findItem(R.id.nav_contact_us)
        nav_about_us = menuFromNAv.findItem(R.id.nav_about_us)
        nav_app_release = menuFromNAv.findItem(R.id.nav_app_release)
        nav_signout = menuFromNAv.findItem(R.id.nav_signout)

        initDrawerItems()


    }

    fun initDrawerItems() {
        if (checkIfUSerExist()) {
            login_nav.setVisible(false)
            update_profile_nav.setEnabled(true)
            nav_my_addresses.setEnabled(true)
            nav_my_orders.setEnabled(true)
            nav_signout.setEnabled(true)

        } else {
            login_nav.setVisible(true)
            update_profile_nav.setEnabled(false)
            nav_my_addresses.setEnabled(false)
            nav_my_orders.setEnabled(false)
            nav_signout.setEnabled(false)
        }
    }


    private fun disableNavigationViewScrollbars(navigationView: NavigationView?) {
        if (navigationView != null) {
            val navigationMenuView =
                navigationView.getChildAt(0) as NavigationMenuView
            if (navigationMenuView != null) {
                navigationMenuView.isVerticalScrollBarEnabled = false
            }
        }
    }

    // navigation drawer//
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_login -> {
                checkProfile()
            }
            R.id.nav_update_profile -> {
                Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_my_addresses -> {
                Toast.makeText(this, "Friends clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_my_cart -> {
                Toast.makeText(this, "Update clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_change_language -> {
                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
            }


            R.id.nav_help -> {
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_rate_us -> {
                Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_share -> {
                Toast.makeText(this, "Friends clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_terms_conditions -> {
                Toast.makeText(this, "Update clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_privacy_policy -> {
                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_contact_us -> {
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_about_us -> {
                Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_signout -> {
                Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show()
                SharedPreference.saveUserProfile(this@MainActivity,null)
                initializeMenuItems()
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    //


    override fun onResume() {
        super.onResume()
        initDrawerItems()

    }


    val navigationItemSelected = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {


            when (item.itemId) {
                R.id.dashboardFrag -> {
                    relCart.visibility = View.VISIBLE
                    relSearchProduct.visibility = View.VISIBLE
                    containerFrame.visibility = View.VISIBLE
                    mainNavigationFragment.visibility = View.INVISIBLE

                }
                R.id.myOrdersFrag -> {
                    setTitle()
                    selectedFragment = MyOrdersSlidersFragments.newInstance("", "")
                    setFragments()
                }
                R.id.offersFrag -> {
                    setTitle()
                    selectedFragment = OffersSliderFragment.newInstance("", "")
                    setFragments()
                }
                R.id.profileFrag -> {
                    if (checkProfile()) {
                        setTitle()
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


    fun setTitle() {
        relCart.visibility = View.INVISIBLE
        relSearchProduct.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        selectedFragment.onActivityResult(requestCode, resultCode, data)
    }


    private val BACK_STACK_ROOT_TAG = "root_fragment"


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

    fun checkIfUSerExist(): Boolean {
        val user = SharedPreference.getUserData(this)
        return if (user != null) {
            user.is_complete_profile == 1
        } else false
    }

    fun checkProfile(): Boolean {
        val user = SharedPreference.getUserData(this)
        if (user != null) {
            if (user.is_complete_profile == 1) {
                return true
            } else {
                if (user.is_register_number == 1) {
                    startActivity(
                        Intent(this, ProfileActivity::class.java).putExtra(
                            "from_main",
                            true
                        )
                    )
                } else {
                    startActivity(
                        Intent(
                            this,
                            MobileRegisterationActivity::class.java
                        ).putExtra("from_main", true)
                    )
                }
                return false
            }
        } else {
            startActivity(
                Intent(
                    this,
                    MobileRegisterationActivity::class.java
                ).putExtra("from_main", true)
            )
            return false
        }
    }

}
