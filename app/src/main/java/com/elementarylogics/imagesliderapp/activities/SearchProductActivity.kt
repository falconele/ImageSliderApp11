package com.elementarylogics.imagesliderapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elementarylogics.imagesliderapp.R
import com.elementarylogics.imagesliderapp.adaptors.searchproductadaptor.SearchProductRecyclerAdaptor
import com.elementarylogics.imagesliderapp.utils.Utility
import kotlinx.android.synthetic.main.activity_search_product.*

class SearchProductActivity : AppCompatActivity(), SearchProductRecyclerAdaptor.ItemClickListner {
    override fun onItemClicklistner(position: Int) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        startActivity(intent)
    }

    lateinit var recSearchProduct: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_product)
        tvIteamCount.setText(Utility.productList.size.toString() + " " + getString(R.string.results_found))
        recSearchProduct = findViewById(R.id.recSearchProduct)
        recSearchProduct!!.setLayoutManager(LinearLayoutManager(applicationContext))
        recSearchProduct.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                LinearLayoutManager.VERTICAL
            )
        )
        runAnimation(recSearchProduct!!)

        imgBack.setOnClickListener(View.OnClickListener {
            setResultsFun()
        })
        imgCross.setOnClickListener(View.OnClickListener {
            etSearchProduct.setText("")
        })


    }

    lateinit var adapter: SearchProductRecyclerAdaptor
    private fun runAnimation(recyclerView: RecyclerView) {


        adapter = SearchProductRecyclerAdaptor(Utility.productList, this, this)
        recSearchProduct.adapter = adapter
//        //recyclerView.layoutAnimation = controller
//        recSearchProduct.adapter!!.notifyDataSetChanged()
        // recyclerView.scheduleLayoutAnimation()

        //some new line added
        //some new line added
    }


    val SEARCH_PROD_REQ_CODE = 110
    fun setResultsFun() {
        val intent = Intent()
        intent.putExtra("name", "amir")
        setResult(RESULT_OK, intent)
        finish()
    }
}
