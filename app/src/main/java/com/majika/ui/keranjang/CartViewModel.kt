package com.majika.ui.keranjang

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.majika.api.cart.CartRepository
import androidx.lifecycle.viewModelScope
import com.majika.api.cart.CartDatabase
import com.majika.api.cart.CartItem
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CartRepository
    val allItems: LiveData<List<CartItem>>

    init {
        val cartItemDao = CartDatabase.getDatabase(application).cartItemDao()
        repository = CartRepository(cartItemDao)
        allItems = repository.allItems
    }

    fun addItem(item: CartItem) = viewModelScope.launch {
        repository.addItem(item)
    }

    fun removeItem(item: CartItem) = viewModelScope.launch {
        repository.removeItem(item)
    }

    fun removeAllItems() = viewModelScope.launch {
        repository.removeAllItems()
    }

    suspend fun getItemByName(Name: String): CartItem? {
        return repository.getItemByName(Name)
    }
}
