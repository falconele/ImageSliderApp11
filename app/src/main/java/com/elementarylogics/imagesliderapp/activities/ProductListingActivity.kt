package com.elementarylogics.imagesliderapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.elementarylogics.imagesliderapp.ProductListingFragment
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.activities.searchproduct.SearchProductActivity
import kotlinx.android.synthetic.main.activity_product_listing.*


class ProductListingActivity : AppCompatActivity() {
    val SEARCH_PROD_REQ_CODE = 110
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_listing)
        setupViewPager(viewpager)
        tabItemsCat.setupWithViewPager(viewpager)
        imgBack.setOnClickListener(View.OnClickListener {
            setResultsFun()
        })
        imgSearchProducts.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SearchProductActivity::class.java)
            startActivityForResult(intent, SEARCH_PROD_REQ_CODE)
        })

    }

    fun setResultsFun() {
        val intent = Intent()
        intent.putExtra("name", "amir")
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ProductListingFragment(), "Cat 1")
        adapter.addFragment(ProductListingFragment(), "Cat 2")
        adapter.addFragment(ProductListingFragment(), "Cat 3")
//
//        adapter.addFragment(MyOrdersSlidersFragments(), "Tutorial")
//        adapter.addFragment(OffersSliderFragment(), "AndroidQuiz")
//        adapter.addFragment(SalesFragment(), "GithubCode")
        viewPager.setAdapter(adapter)
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) :
        FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()


        override fun getCount(): Int {
            return mFragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return mFragmentList.get(position)
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList.get(position)
        }
    }
}
