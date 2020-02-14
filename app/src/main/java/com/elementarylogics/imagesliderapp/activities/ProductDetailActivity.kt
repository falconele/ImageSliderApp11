package com.elementarylogics.imagesliderapp.activities

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.elementarylogics.imagesliderapp.R
import com.example.parsaniahardik.kotlin_image_slider.ImageModel

import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.activity_product_detail.*

import android.net.Uri





class ProductDetailActivity : AppCompatActivity(),ProductDetailImagesPagerAdaptor.ItemClickListener {
    override fun onImageClickListner(position: Int) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.setDataAndType(Uri.parse("https://cdn.pixabay.com/photo/2015/12/01/20/28/road-1072823_960_720.jpg"), "image/*")
        startActivity(intent)
    }

    var a:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        tvOrignalPrice.setPaintFlags(tvOrignalPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        initViewPager()

        btnAdd.setOnClickListener(View.OnClickListener {
            //            Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
            relAdd.visibility = View.GONE
//           tvQuantity.setText(employee.itemQuantity++.toString())
            relAddMinusQuantity.visibility = View.VISIBLE

        })
        imgAdd.setOnClickListener(View.OnClickListener {
            //            Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
//            tvQuantity.setText(employee.itemQuantity++.toString())
            tvQuantity.setText(a++.toString())
        })
        imgMinus.setOnClickListener(View.OnClickListener {
            //            Toast.makeText(context, position.toString(), Toast.LENGTH_SHORT).show()
//           tvQuantity.setText(employee.itemQuantity++.toString())

            tvQuantity.setText(a--.toString())
        })
        imgBack.setOnClickListener(View.OnClickListener {
            setResultsFun()
        })
    }

    fun setResultsFun() {
        val intent = Intent()
        intent.putExtra("name", "amir")
        setResult(RESULT_OK, intent)
        finish()
    }


    private var imageModelArrayList: ArrayList<ImageModel>? = null
    private val myImageList = intArrayOf(
        R.drawable.harley2,
        R.drawable.benz2,
        R.drawable.vecto
    )

    private fun populateList(): ArrayList<ImageModel> {

        val list = ArrayList<ImageModel>()

        for (i in 0..2) {
            val imageModel = ImageModel()
            imageModel.setImage_drawables(myImageList[i])
            list.add(imageModel)
        }

        return list
    }
    fun initViewPager(){
        imageModelArrayList=populateList()
        pager!!.adapter =
            ProductDetailImagesPagerAdaptor(this!!, this.imageModelArrayList!!, this)

        val indicator = findViewById(R.id.indicator) as CirclePageIndicator

        indicator.setViewPager(pager)

        val density = resources.displayMetrics.density

        //Set circle indicator radius
        indicator.setRadius(5 * density)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResultsFun()
    }
}
