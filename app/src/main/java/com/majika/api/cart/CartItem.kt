package com.majika.api.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val name: String,
    val price: Int,
    var quantity: Int = 1,
)