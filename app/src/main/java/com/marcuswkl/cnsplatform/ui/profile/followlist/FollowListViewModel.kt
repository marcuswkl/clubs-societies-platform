package com.marcuswkl.cnsplatform.ui.profile.followlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FollowListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Follow List Fragment"
    }
    val text: LiveData<String> = _text
}