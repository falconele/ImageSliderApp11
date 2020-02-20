package com.elementarylogics.imagesliderapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.adaptors.mycaradaptort.MyCartAdaptor
import com.elementarylogics.imagesliderapp.utils.Utility
import kotlinx.android.synthetic.main.activity_my_cart.*

class MyCartActivity : AppCompatActivity(), MyCartAdaptor.ItemClickListner {


    override fun onItemClicklistner(position: Int) {
//        val intent = Intent(this, ProductDetailActivity::class.java)
//        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart)
        recMyCart!!.setLayoutManager(LinearLayoutManager(applicationContext))
        recMyCart.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                LinearLayoutManager.VERTICAL
            )
        )
        runAnimation(recMyCart!!)

        relLastMinuteBuy.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, LastMinuteBuyActivity::class.java))
        })

        btnProceddCheckout.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, MobileRegisterationActivity::class.java))
        })
    }

    lateinit var adapter: MyCartAdaptor
    private fun runAnimation(recyclerView: RecyclerView) {


        adapter = MyCartAdaptor(Utility.productList, applicationContext!!, this)
        recMyCart.adapter = adapter
//        //recyclerView.layoutAnimation = controller
//        recSearchProduct.adapter!!.notifyDataSetChanged()
        // recyclerView.scheduleLayoutAnimation()

        //some new line added
        //some new line added
    }

}
