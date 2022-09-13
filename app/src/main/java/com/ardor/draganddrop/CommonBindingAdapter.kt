package com.ardor.draganddrop

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardor.draganddrop.adapter.SampleDrag2Adapter
import com.ardor.draganddrop.adapter.SampleDragAdapter
import com.ardor.draganddrop.listener.CustomListener
import com.ardor.draganddrop.listener.DragListener
import com.ardor.draganddrop.model.SimpleModel

object CommonBindingAdapter {
    @BindingAdapter(
        value = ["item", "listener"]
    )
    @JvmStatic
    fun bindRecyclerView3(
        view: RecyclerView,
        data: List<SimpleModel>?,
        listener: CustomListener
    ) {
        view.setHasFixedSize(true)
        view.setOnDragListener(
            DragListener(
                listener,
                R.id.front_recycler_view,
                R.id.behind_recycler_view
            )
        )
        data.let {
            val adapter = view.adapter as? SampleDragAdapter
            adapter?.submitList(data) ?: run {
                view.layoutManager = GridLayoutManager(view.context, 3)
                view.adapter = SampleDragAdapter(listener).apply {
                    submitList(data)
                }
            }
        }
    }


    @BindingAdapter(
        value = ["data", "listener"]
    )
    @JvmStatic
    fun bindRecyclerView2(
        view: RecyclerView,
        data: List<SimpleModel?>?,
        listener: CustomListener
    ) {
        view.setHasFixedSize(true)
        view.setOnDragListener(
            DragListener(
                listener,
                R.id.front_recycler_view,
                R.id.behind_recycler_view
            )
        )
        data.let {
            val adapter = view.adapter as? SampleDrag2Adapter
            adapter?.submitList(data) ?: run {
                view.layoutManager = GridLayoutManager(view.context, 3)
                view.adapter = SampleDrag2Adapter(listener).apply {
                    submitList(data)
                }
            }
        }
    }
}