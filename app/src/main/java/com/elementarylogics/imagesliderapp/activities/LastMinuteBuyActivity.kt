package com.elementarylogics.imagesliderapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.adaptors.lastminutebuyadaptor.LastMinuteBuyAdaptor
import com.elementarylogics.imagesliderapp.adaptors.mycaradaptort.MyCartAdaptor
import com.elementarylogics.imagesliderapp.utils.Utility
import kotlinx.android.synthetic.main.activity_last_minute_buy.*
import kotlinx.android.synthetic.main.activity_my_cart.*

class LastMinuteBuyActivity : AppCompatActivity(), LastMinuteBuyAdaptor.ItemClickListner {
    override fun onItemClicklistner(position: Int) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last_minute_buy)
        recLastMinBuy!!.setLayoutManager(LinearLayoutManager(applicationContext))
        recLastMinBuy.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                LinearLayoutManager.VERTICAL
            )
        )
        runAnimation(recLastMinBuy!!)
    }

    lateinit var adapter: LastMinuteBuyAdaptor
    private fun runAnimation(recyclerView: RecyclerView) {


        adapter = LastMinuteBuyAdaptor(Utility.productList, applicationContext!!, this)
        recyclerView.adapter = adapter
//        //recyclerView.layoutAnimation = controller
//        recSearchProduct.adapter!!.notifyDataSetChanged()
        // recyclerView.scheduleLayoutAnimation()

        //some new line added
        //some new line added
    }
}
