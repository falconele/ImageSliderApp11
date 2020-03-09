package com.elementarylogics.imagesliderapp.dataclases

data class User(
    var id: Int,
    var first_name: String,
    var last_name: String,
    var fullImagePath: String,
    var email: String,
    var latitude: Double,
    val longitude: Double,
    var phone_number: String,
    var address: String,
    var city: String,
    var photo: String,
    var status: Int,
    var house_flate_number: String,
    var area_colony: String,
    var token: String
)