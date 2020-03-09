package com.elementarylogics.imagesliderapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.adaptors.lastminutebuyadaptor.LastMinuteBuyAdaptor
import com.elementarylogics.imagesliderapp.utils.Utility
import kotlinx.android.synthetic.main.activity_last_minute_buy.*

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

        btnProceddCheckout.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, MobileRegisterationActivity::class.java))
        })
        imgBack.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    lateinit var adapter: LastMinuteBuyAdaptor
    private fun runAnimation(recyclerView: RecyclerView) {
        adapter = LastMinuteBuyAdaptor(Utility.productList, applicationContext!!, this)
        recyclerView.adapter = adapter
    }
}
