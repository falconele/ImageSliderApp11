package com.elementarylogics.imagesliderapp.dataclases

data class Product(
    val id: Int,
    val name: String,
    val priceOrigonal: String,
    val discountedPrice: String,
    val unit: String,
    val off: String,
    val deliveryType: String,
    val imgProductPath: String,
    var itemQuantity: Int=0
)