package com.ardor.draganddrop

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardor.draganddrop.adapter.SampleDragAdapter

object CommonBindingAdapter {

    @BindingAdapter(
        value = ["item", "block"],
        requireAll = false
    )
    @JvmStatic
    fun bindRecyclerView3(
        view: RecyclerView,
        data: List<String>?,
        block : Boolean
    ) {
        view.setHasFixedSize(true)
        data.let {
            val adapter = view.adapter as? SampleDragAdapter
            adapter?.submitList(data) ?: run {
                view.layoutManager = GridLayoutManager(view.context, 3)
                view.adapter = SampleDragAdapter(block).apply {
                    submitList(data)
                }
            }
        }
    }

}