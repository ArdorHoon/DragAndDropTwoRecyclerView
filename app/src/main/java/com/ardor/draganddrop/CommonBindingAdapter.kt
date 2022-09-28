package com.ardor.draganddrop

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardor.draganddrop.adapter.SimpleAdapter
import com.ardor.draganddrop.model.SimpleModel

object CommonBindingAdapter {

    @BindingAdapter(
        value = ["data", "onAdapterListener", "isSwappable"]
    )
    @JvmStatic
    fun bindGeneralDragRecyclerView(
        view: RecyclerView,
        data: List<SimpleModel>?,
        onAdapterListener: SimpleAdapter.OnAdapterListener,
        isSwappable: Boolean?,
    ) {
        view.setHasFixedSize(true)
        data?.let { items ->
            val adapter = view.adapter as? SimpleAdapter
            adapter?.submitList(items) ?: run {
                view.layoutManager = GridLayoutManager(view.context, 3)
                view.adapter =
                    SimpleAdapter(isSwappable ?: false, onAdapterListener).apply {
                        submitList(items)
                        view.setOnDragListener(dragListener)
                    }
            }
        }
    }

}