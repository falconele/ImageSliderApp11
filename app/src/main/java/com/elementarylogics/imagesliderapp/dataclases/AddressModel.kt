package com.elementarylogics.imagesliderapp.dataclases

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressModel(
    val id: Int,
    val gender: String,
    val name: String,
    val mail: String,
    val flatHouse: String,
    val areaColony: String,
    val city: String,
    val addressType: String,
    val address: String,
    val latatude: Double,
    val longitude: Double
): Parcelable

