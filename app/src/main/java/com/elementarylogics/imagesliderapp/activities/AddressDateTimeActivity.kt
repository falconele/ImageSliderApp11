package com.elementarylogics.imagesliderapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elementarylogics.imagesliderapp.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_address_date_time.*


class AddressDateTimeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_date_time)




        imgEdit.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, AddressesActivity::class.java))
        })

        relDateandTime.setOnClickListener(View.OnClickListener {
            showBottomSheetDialog()

        })

        linItemsInCart.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,MyCartActivity::class.java))

        })
    }


    lateinit var imgCross: ImageView
    lateinit var btnDone: Button
    lateinit var dateNumberPicker: NumberPicker
    val dates = arrayOf(
        "tomorrow  10:00 AM - 10:00 PM",
        "feb 21, 2020  10:00 AM - 10:00 PM",
        "feb 22, 2020  10:00 AM - 10:00 PM"
    )

    fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottomsheet_date_and_time, null)
        imgCross = view.findViewById(R.id.imgCross)
        btnDone = view.findViewById(R.id.btnDone)
        dateNumberPicker = view.findViewById(R.id.dateNumberPicker)
        dateNumberPicker.displayedValues = dates
        dateNumberPicker.maxValue = 2
        dateNumberPicker.minValue = 0
        dateNumberPicker.setOnValueChangedListener { numberPicker, i, i1 ->
            val valuePicker1 = dateNumberPicker.value
            Toast.makeText(applicationContext, dates.get(valuePicker1), Toast.LENGTH_SHORT).show()

        }

        val dialog = BottomSheetDialog(this)
        dialog.setCancelable(false)
        btnDone.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        imgCross.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })


        dialog.setContentView(view)
        dialog.show()
    }
}
