package com.ardor.draganddrop.legacy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<ITEM : Any, VB : ViewDataBinding>(
    parent: ViewGroup,
    @LayoutRes
    layoutRes: Int
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)) {

    abstract val binding: VB
    abstract fun bind(item: ITEM)
}