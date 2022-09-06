package com.ardor.draganddrop.helper

import androidx.recyclerview.widget.RecyclerView

interface OnStartDragListener {
    fun onStartDrag(viewHolder : RecyclerView.ViewHolder?)
}