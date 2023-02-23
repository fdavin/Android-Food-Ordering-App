package com.majika.api.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey val name: String,
    val price: Int,
    var quantity: Int,
)

fun List<CartItem>.asDomainModel(): ArrayList<CartItem> {
    return map {
        CartItem(
            name = it.name,
            price = it.price,
            quantity = it.quantity
        )
    } as ArrayList<CartItem>
}

