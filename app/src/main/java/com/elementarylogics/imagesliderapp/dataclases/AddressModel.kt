package com.elementarylogics.imagesliderapp.dataclases

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


data class AddressModel(
    val id: Int,
    val gender: String,
    val name: String,
    val email: String,
    val address_type: String,
    val latitude: Double,
    val address: String,
    val customer_id: Int,
    val longitude: Double,
    val city: String,
    val image: String,
    val fullImagePath: String,
    val area_colony: String,
    val house_flate_number: String
 ) : Serializable

