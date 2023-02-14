package com.majika.ui.restoran

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RestoranViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Restoran"
    }
    val text: LiveData<String> = _text
}