package com.majika.api.cart

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartRepository(private val database: CartDatabase) {
    private val cartItemDao = database.cartItemDao()
    val keranjang = database.cartItemDao().getAll()
    val total = database.cartItemDao().getTotal()

    suspend fun addItem(item: CartItem) {
        withContext(Dispatchers.IO) {
            cartItemDao.insert(item)
        }
    }

    suspend fun updateItem(item: CartItem) {
        withContext(Dispatchers.IO) {
            cartItemDao.update(item)
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
