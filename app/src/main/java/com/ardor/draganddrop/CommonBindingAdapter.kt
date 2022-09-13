package com.ardor.draganddrop

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardor.draganddrop.adapter.SampleDrag2Adapter
import com.ardor.draganddrop.adapter.SampleDragAdapter
import com.ardor.draganddrop.listener.DragListener
import com.ardor.draganddrop.model.SimpleModel

object CommonBindingAdapter {



    @BindingAdapter(
        value = ["item"],
        requireAll = false
    )
    @JvmStatic
    fun bindRecyclerView3(
        view: RecyclerView,
        data: List<SimpleModel>?,
    ) {
        view.setHasFixedSize(true)
        view.setOnDragListener(DragListener())
        data.let {
            val adapter = view.adapter as? SampleDragAdapter
            adapter?.submitList(data) ?: run {
                view.layoutManager = GridLayoutManager(view.context, 3)
                view.adapter = SampleDragAdapter().apply {
                    submitList(data)
                }
            }
        }
    }


    @BindingAdapter(
        value = ["data"],
        requireAll = false
    )
    @JvmStatic
    fun bindRecyclerView2(
        view: RecyclerView,
        data: List<SimpleModel>?,
    ) {
        view.setHasFixedSize(true)
        view.setOnDragListener(DragListener())
        data.let {
            val adapter = view.adapter as? SampleDrag2Adapter
            adapter?.submitList(data) ?: run {
                view.layoutManager = GridLayoutManager(view.context, 3)
                view.adapter = SampleDrag2Adapter().apply {
                    submitList(data)
                }
            }
        }
    }
}