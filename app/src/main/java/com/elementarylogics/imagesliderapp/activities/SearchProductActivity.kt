package com.elementarylogics.imagesliderapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elementarylogics.expandablerecyclerviewkotlin.ParentRecyclerAdapter
import com.elementarylogics.imagesliderapp.R
import kotlinx.android.synthetic.main.activity_search_product.*

class SearchProductActivity : AppCompatActivity() {

    lateinit var recSearchProduct:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_product)
        recSearchProduct=findViewById(R.id.recSearchProduct)
        recSearchProduct!!.setLayoutManager(LinearLayoutManager(applicationContext))
        runAnimation(recSearchProduct!!)


        imgBack.setOnClickListener(View.OnClickListener {
            setResultsFun()
        })
        imgCross.setOnClickListener(View.OnClickListener {
            etSearchProduct.setText("")
        })




    }


    private fun runAnimation(recyclerView: RecyclerView) {





//        adapter = ParentRecyclerAdapter(context, this)
//        recSearchProduct.adapter = adapter
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
