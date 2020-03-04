package com.elementarylogics.imagesliderapp.utils

import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.elementarylogics.imagesliderapp.dataclases.AddressModel
import com.elementarylogics.imagesliderapp.dataclases.Product

class Utility {
    companion object {
        var productList: ArrayList<Product> = ArrayList()
        var address: ArrayList<AddressModel> = ArrayList()
        var MY_PERMISSIONS_REQUEST_LOCATION: Int = 99
        var isEditExtra: String = "isEditExtra"
        var addressExtra = "address"


        fun showProgressBar(
            activity: AppCompatActivity,
            progressBar: ProgressBar,
            progressVisible: Boolean
        ) {
             progressBar.setVisibility(if (progressVisible) View.VISIBLE else View.GONE)

        }

    }
}