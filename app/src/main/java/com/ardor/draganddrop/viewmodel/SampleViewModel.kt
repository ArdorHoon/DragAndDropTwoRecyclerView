package com.ardor.draganddrop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardor.draganddrop.model.SimpleModel

class SampleViewModel : ViewModel() {
    private val _bottomData: MutableLiveData<List<SimpleModel?>> = MutableLiveData()
    val bottomData: LiveData<List<SimpleModel?>> = _bottomData

    var tempData: MutableList<SimpleModel?> =
        arrayListOf(SimpleModel("one", true), null, null)

    private val _topData: MutableLiveData<List<SimpleModel?>> = MutableLiveData()
    val topData: LiveData<List<SimpleModel?>> = _topData

    fun setData(items: List<SimpleModel>) {
        _bottomData.value = items
    }

    fun setTopData(list: MutableList<SimpleModel?>) {
        _topData.value = list
    }

    fun setBottomData(list: MutableList<SimpleModel?>) {
        _bottomData.value = list
    }

    init {
        _topData.value = tempData
    }
}