package com.elementarylogics.imagesliderapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.elementarylogics.imagesliderapp.R
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        cardJazCash.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, OrderSuccessActivity::class.java))
        })
        cardEasyPaisa.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, OrderCancelActivity::class.java))
        })
    }
}
