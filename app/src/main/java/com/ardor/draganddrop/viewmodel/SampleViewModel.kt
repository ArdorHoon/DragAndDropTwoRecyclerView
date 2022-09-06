package com.ardor.draganddrop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SampleViewModel : ViewModel() {

    private val _data: MutableLiveData<List<String>> = MutableLiveData()
    val data: LiveData<List<String>> = _data

    fun setData(items: List<String>) {
        _data.value = items
    }
}