package com.ardor.draganddrop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardor.draganddrop.model.SimpleModel

class SampleViewModel : ViewModel() {

    private val _data: MutableLiveData<List<SimpleModel?>> = MutableLiveData()
    val data: LiveData<List<SimpleModel?>> = _data

    var tempData: MutableList<SimpleModel?> =
        arrayListOf(SimpleModel("one", false), null, null)

    private val _data2: MutableLiveData<List<SimpleModel?>> = MutableLiveData()
    val data2: LiveData<List<SimpleModel?>> = _data2

    fun setData(items: List<SimpleModel>) {
        _data.value = items
    }

    fun setTopData(list : MutableList<SimpleModel?>) {
        _data2.value = list
    }

    fun setBottomData(list : MutableList<SimpleModel?>) {
        _data.value = list
    }

    init {
        _data2.value = tempData
    }
}