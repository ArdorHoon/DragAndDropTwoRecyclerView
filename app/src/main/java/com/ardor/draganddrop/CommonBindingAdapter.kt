package com.ardor.draganddrop

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ardor.draganddrop.adapter.SampleAdapter
import com.ardor.draganddrop.helper.MyItemTouchHelperCallback
import com.ardor.draganddrop.helper.OnStartDragListener

object CommonBindingAdapter {
    @BindingAdapter(
        value = ["data"],
        requireAll = false
    )
    @JvmStatic
    fun bindRecyclerView(
        view: RecyclerView,
        data: List<String>?,
    ) {
        var itemTouchHelper : ItemTouchHelper? = null
        view.setHasFixedSize(true)
        data.let {
            val adapter = view.adapter as? SampleAdapter
            adapter?.submitList(data) ?: run {
                view.layoutManager = GridLayoutManager(view.context, 2)
                view.adapter = SampleAdapter(object : OnStartDragListener {
                    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
                        viewHolder?.let {
                            itemTouchHelper?.startDrag(it)
                        }
                    }
                }).apply {
                    val callback = MyItemTouchHelperCallback(this)
                    itemTouchHelper = ItemTouchHelper(callback)
                    itemTouchHelper?.attachToRecyclerView(view)
                    submitList(data)
                }
            }
        }
    }


}