package com.marcuswkl.cnsplatform.ui.search.leadership

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LeadershipViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is search leadership Fragment"
    }
    val text: LiveData<String> = _text
}
