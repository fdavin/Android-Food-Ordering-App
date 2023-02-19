package com.majika.api.cart

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getAll(): LiveData<List<CartItem>>

    @Query("SELECT * FROM cart_items WHERE name = :name")
    fun getByName(name: String): CartItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: CartItem)

    @Delete
    fun delete(item: CartItem)

    @Query("DELETE FROM cart_items")
    fun deleteAll()
}

