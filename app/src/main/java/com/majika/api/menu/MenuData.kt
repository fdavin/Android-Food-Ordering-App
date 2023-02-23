package com.majika.api.menu

data class MenuData(
    val name: String,
    val description: String,
    val currency: String,
    val price: Int,
    val sold: Int,
    val type: String
)