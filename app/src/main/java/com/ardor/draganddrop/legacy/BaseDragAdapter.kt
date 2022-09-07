package com.ardor.draganddrop.legacy

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter

abstract class BaseDragAdapter<VB : ViewDataBinding, ITEM : Any>(@LayoutRes val layoutId: Int) :
    ListAdapter<ITEM, BaseViewHolder<ITEM, VB>>(BaseDiffUtil<ITEM>()) {

    abstract fun onCreateViewDataBinding(itemView: View): VB
    abstract fun bind(item: ITEM, binding: VB, adapterPosition: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ITEM, VB> {
        return object : BaseViewHolder<ITEM, VB>(parent, layoutId) {
            override val binding: VB = onCreateViewDataBinding(itemView)
            override fun bind(item: ITEM) {
                bind(item, binding, adapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ITEM, VB>, position: Int) {
        holder.bind(getItem(position))
    }
}

