package com.ardor.draganddrop.listener

import com.ardor.draganddrop.model.SimpleModel


interface CustomListener {
    fun getMaxSize(): Int
    fun setTopData(list: MutableList<SimpleModel?>)
    fun setBottomData(list: MutableList<SimpleModel?>)
}
