package com.elementarylogics.imagesliderapp.dataclases

data class Order(
    val id: Int,
    val dtProcessed: String,
    val dtCreated: String,
    val productList: String,
    val address: String,
    val benificieryName: String,
    val amount: String,
    val status: String,
    val benificieryImg: String
)