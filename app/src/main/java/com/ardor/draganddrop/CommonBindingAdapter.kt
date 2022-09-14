package com.ardor.draganddrop

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardor.draganddrop.adapter.DragAdapter
import com.ardor.draganddrop.listener.CustomListener
import com.ardor.draganddrop.listener.DragListener
import com.ardor.draganddrop.model.SimpleModel

object CommonBindingAdapter {

    @BindingAdapter(
        value = ["item", "listener"]
    )
    @JvmStatic
    fun bindDragRecyclerview(
        view: RecyclerView,
        data: List<SimpleModel?>?,
        listener: CustomListener
    ) {
        val dragListener: DragListener =
            object : DragListener(
                listener,
                R.id.top_recycler_view,
                R.id.bottom_recycler_view
            ) {
                override val topMaxItemCount: Int
                    get() = 3
                override val bottomMaxItemCount: Int
                    get() = 0
            }

        view.setHasFixedSize(true)
        view.setOnDragListener(dragListener)
        data.let {
            val adapter = view.adapter as? DragAdapter
            adapter?.submitList(data) ?: run {
                view.layoutManager = GridLayoutManager(view.context, 3)
                view.adapter = DragAdapter(listener).apply {
                    submitList(data)
                }
            }
        }
    }
}