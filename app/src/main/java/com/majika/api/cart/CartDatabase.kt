package com.majika.api.cart

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

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

@Database(entities = [CartItem::class], version = 1)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartItemDao(): CartDao
}
private lateinit var INSTANCE: CartDatabase
fun getDatabase(context: Context): CartDatabase {
    synchronized(CartDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                CartDatabase::class.java,
                "cart_items"
            ).build()
        }
    }
    return INSTANCE
}