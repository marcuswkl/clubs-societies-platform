package com.marcuswkl.cnsplatform.ui.enquire

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EnquireViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is enquire Fragment"
    }
    val text: LiveData<String> = _text
}