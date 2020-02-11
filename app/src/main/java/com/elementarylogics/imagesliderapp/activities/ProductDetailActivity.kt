package com.elementarylogics.imagesliderapp.activities

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.elementarylogics.imagesliderapp.R
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {

    var a:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        tvOrignalPrice.setPaintFlags(tvOrignalPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
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

    override fun onBackPressed() {
        super.onBackPressed()
        setResultsFun()
    }
}
