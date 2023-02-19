package com.majika.api.cart

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartRepository(private val cartItemDao: CartDao) {
    
    val allItems: LiveData<List<CartItem>> = cartItemDao.getAll()

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
