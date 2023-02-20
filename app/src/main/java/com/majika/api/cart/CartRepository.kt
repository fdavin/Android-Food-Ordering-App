package com.majika.api.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartRepository(private val database: CartDatabase) {
    private val cartItemDao = database.cartItemDao()
    val keranjang = database.cartItemDao().getAll()
    //val keranjang1: LiveData<List<CartItem>> = Transformations.map(database.cartItemDao().getAll()) {
    //    it.asDomainModel()
    //}
    suspend fun addItem(item: CartItem) {
        withContext(Dispatchers.IO) {
            cartItemDao.insert(item)
        }
    }

    suspend fun removeItem(item: CartItem) {
        withContext(Dispatchers.IO) {
            cartItemDao.delete(item)
        }
    }

    suspend fun removeAllItems() {
        withContext(Dispatchers.IO) {
            cartItemDao.deleteAll()
        }
    }

    suspend fun getItemByName(Name: String): CartItem? {
        return withContext(Dispatchers.IO) {
            cartItemDao.getByName(Name)
        }
    }
}
