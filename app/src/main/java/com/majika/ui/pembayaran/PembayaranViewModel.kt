package com.majika.ui.pembayaran

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PembayaranViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is pembayaran Fragment"
    }
    val text: LiveData<String> = _text
}