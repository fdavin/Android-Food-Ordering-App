package com.majika.ui.keranjang

import android.app.Application
import androidx.lifecycle.*
import com.majika.api.cart.*
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CartRepository(getDatabase(application))

    val keranjang = repository.keranjang
    private val _playlist = MutableLiveData<List<CartItem>>()
    val total = repository.total
    fun addItem(item: CartItem) = viewModelScope.launch {
        repository.addItem(item)
    }

    fun removeItem(item: CartItem) = viewModelScope.launch {
        repository.removeItem(item)
    }

    fun removeAllItems() = viewModelScope.launch {
        repository.removeAllItems()
    }

    fun updateItem(item: CartItem) = viewModelScope.launch {
        repository.updateItem(item)
    }
    suspend fun getItemByName(Name: String): CartItem? {
        return repository.getItemByName(Name)
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
