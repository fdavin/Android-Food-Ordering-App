package com.majika.ui.keranjang

import android.app.Application
import androidx.lifecycle.*
import com.majika.api.cart.*
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CartRepository(getDatabase(application))

    val keranjang = repository.keranjang
    val total = repository.total
    fun addItem(item: CartItem) = viewModelScope.launch {
        repository.addItem(item)
    }

    fun removeItem(item: CartItem) = viewModelScope.launch {
        repository.removeItem(item)
    }

    fun updateItem(item: CartItem) = viewModelScope.launch {
        repository.updateItem(item)
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CartViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
