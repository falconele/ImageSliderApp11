package com.elementarylogics.imagesliderapp.utils

import com.elementarylogics.imagesliderapp.dataclases.AddressModel
import com.elementarylogics.imagesliderapp.dataclases.Product

class Utility {
    companion object {
        var productList: ArrayList<Product> = ArrayList()
        var address: ArrayList<AddressModel> = ArrayList()
        var MY_PERMISSIONS_REQUEST_LOCATION:Int = 99
        var isEditExtra:String="isEditExtra"
        var addressExtra="address"




    }
}