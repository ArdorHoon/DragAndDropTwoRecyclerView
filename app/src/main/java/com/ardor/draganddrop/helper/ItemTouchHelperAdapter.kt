package com.ardor.draganddrop.helper

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition : Int, toPosition : Int) : Boolean
    fun onItemDismiss(position: Int)
}