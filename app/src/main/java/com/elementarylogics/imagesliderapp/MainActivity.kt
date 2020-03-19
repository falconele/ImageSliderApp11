package com.elementarylogics.imagesliderapp


import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.elementarylogics.imagesliderapp.activities.*
import com.elementarylogics.imagesliderapp.activities.searchproduct.SearchProductActivity
import com.elementarylogics.imagesliderapp.fragments.MyOrdersSlidersFragments
import com.elementarylogics.imagesliderapp.fragments.OffersFragment
import com.elementarylogics.imagesliderapp.fragments.OffersSliderFragment
import com.elementarylogics.imagesliderapp.fragments.profile.ProfileSliderFragment
import com.elementarylogics.imagesliderapp.utils.Constants
import com.elementarylogics.imagesliderapp.utils.SharedPreference
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenuView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ProfileSliderFragment.OnFragmentInteractionListener {
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
    lateinit var tvLanguage:TextView

    lateinit var imgProfile: ImageView
    lateinit var tvName: TextView

    val SEARCH_PROD_REQ_CODE = 110


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

        val headerView = navView.getHeaderView(0)
        val languageActionLayout=nav_language.actionView
        tvLanguage=languageActionLayout.findViewById(R.id.tvLanguage)

        tvLanguage.setText(SharedPreference.getAppLanguage(this))



        imgProfile = headerView.findViewById(R.id.imgProfile)
        tvName = headerView.findViewById(R.id.tvName)

        initDrawerItems()

        relCart.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@MainActivity, MyCartActivity::class.java))


        })

        etSearchProduct.setOnClickListener(View.OnClickListener {

            val intent = Intent(this@MainActivity, SearchProductActivity::class.java)
            startActivityForResult(intent, SEARCH_PROD_REQ_CODE)

        })
    }

    fun initDrawerItems() {
        if (checkIfUSerExist()) {
            login_nav.setVisible(false)
            update_profile_nav.setEnabled(true)
            nav_my_addresses.setEnabled(true)
            nav_my_orders.setEnabled(true)
            nav_signout.setEnabled(true)
            val user = SharedPreference.getUserData(this)

            var requestOptions = RequestOptions()
            requestOptions.error(R.drawable.ic_user)
            requestOptions.placeholder(R.drawable.ic_user)
            requestOptions.circleCrop()

            Glide.with(MainActivity@ this).setDefaultRequestOptions(requestOptions)
                .load(user.fullImagePath)
                .into(imgProfile)
            tvName.setText(user.first_name)


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
        drawerLayout.closeDrawer(GravityCompat.START)
//        Thread.sleep(500)
        Timer("", false).schedule(300) {
            runOnUiThread(Runnable {
                when (item.itemId) {
                    R.id.nav_login -> {
                        checkProfile()
                    }
                    R.id.nav_update_profile -> {
                        bottomNavigationView.selectedItemId = R.id.profileFrag
                    }
                    R.id.nav_my_addresses -> {
                        startActivity(
                            Intent(this@MainActivity, AddressesActivity::class.java).putExtra(
                                "from_main",
                                true
                            )
                        )
                    }
                    R.id.nav_my_cart -> {
                        startActivity(Intent(this@MainActivity, MyCartActivity::class.java))
                    }

                    R.id.nav_my_orders -> {
                        bottomNavigationView.selectedItemId = R.id.myOrdersFrag
                    }

                    R.id.nav_change_language -> {
                        changeLanDialog()
                    }


                    R.id.nav_help -> {
//                    Toast./makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.nav_rate_us -> {
//                    Toast.makeText(this, "Messages clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.nav_share -> {
//                    Toast.makeText(this, "Friends clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.nav_terms_conditions -> {
//                    Toast.makeText(this, "Update clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.nav_privacy_policy -> {
//                    Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
                    }

                    R.id.nav_contact_us -> {
//                    Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
                    }
                    R.id.nav_about_us -> {
                        startContentActivity("about_us", resources.getString(R.string.about_us))
                    }

                    R.id.nav_signout -> {
                        logoutDialog()

                    }

                }
            })

        }
        return true
    }
    //

    fun startContentActivity(key: String, title: String) {
        startActivity(
            Intent(this@MainActivity, ContentPagesActivity::class.java).putExtra(
                "key",
                key
            ).putExtra("title", title)
        )

    }

    override fun onResume() {
        super.onResume()
        initDrawerItems()

    }


    val navigationItemSelected = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {


            when (item.itemId) {
                R.id.dashboardFrag -> {
                    selectedFragment = dashboradSliderFragment
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
        tvTitle.setText("Amir")
        relCart.visibility = View.INVISIBLE
        relSearchProduct.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        selectedFragment.onActivityResult(requestCode, resultCode, data)
    }


    private val BACK_STACK_ROOT_TAG = "root_fragment"


    override fun onBackPressed() {
        if (selectedFragment != dashboradSliderFragment) {
            bottomNavigationView.selectedItemId = R.id.dashboardFrag
//            supportFragmentManager.beginTransaction().hide(activeFragment)
//                .show(dashboradSliderFragment).commit();
//            activeFragment = dashboradSliderFragment
        } else {
            exitDialog()

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


    private fun logoutDialog() {
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        // Set a title for alert dialog
        builder.setTitle(resources.getString(R.string.are_you_sure))

        // Set a message for alert dialog
//        builder.setMessage("Are you sure to Log out?")


        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show()
                    SharedPreference.saveUserProfile(this@MainActivity, null)
                    initializeMenuItems()
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                }

            }
        }


        // Set the alert dialog positive/yes button
        builder.setPositiveButton(resources.getString(R.string.yes), dialogClickListener)

        // Set the alert dialog negative/no button
        builder.setNegativeButton(resources.getString(R.string.no), dialogClickListener)

        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }

    private fun exitDialog() {
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)


        builder.setTitle(resources.getString(R.string.exit_app))


        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    super.onBackPressed()
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                }

            }
        }


        // Set the alert dialog positive/yes button
        builder.setPositiveButton(resources.getString(R.string.yes), dialogClickListener)

        // Set the alert dialog negative/no button
        builder.setNegativeButton(resources.getString(R.string.no), dialogClickListener)

        // Initialize the AlertDialog using builder object
        dialog = builder.create()
        // Finally, display the alert dialog
        dialog.show()
    }

    private fun changeLanDialog() {
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)


        builder.setTitle(resources.getString(R.string.exit_app))


        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    SharedPreference.setAppLanguage(this,Constants.english)
                    tvLanguage.setText(resources.getString(R.string.eng))
                    changeLanguage(Constants.english)

                }
                DialogInterface.BUTTON_NEGATIVE -> {
                    SharedPreference.setAppLanguage(this,Constants.swedish)
                    tvLanguage.setText(resources.getString(R.string.swedish))
                    changeLanguage(Constants.swedish)

                }

            }
        }


        // Set the alert dialog positive/yes button
        builder.setPositiveButton(resources.getString(R.string.english), dialogClickListener)

        // Set the alert dialog negative/no button
        builder.setNegativeButton(resources.getString(R.string.swedish), dialogClickListener)
        builder.setNeutralButton(resources.getString(R.string.cancel_dialog), dialogClickListener)

        // Initialize the AlertDialog using builder object
        dialog = builder.create()
        // Finally, display the alert dialog
        dialog.show()
    }

    private fun changeLanguage(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, null)
        SharedPreference.setAppLanguage(this@MainActivity, language)
        val lang = SharedPreference.getAppLanguage(this@MainActivity)
        startActivity(Intent(this@MainActivity, MainActivity::class.java))
        finish()
        //        finishAffinity();
    }

    override fun onFragmentInteraction(updated: Boolean) {
        if (updated) {
//            Toast.makeText(applicationContext, "Profile updated", Toast.LENGTH_SHORT).show()
            initDrawerItems()
        }
    }


}
