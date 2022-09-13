package com.ardor.draganddrop.adapter

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ardor.draganddrop.R
import com.ardor.draganddrop.databinding.CardItemBinding
import com.ardor.draganddrop.listener.CustomListener
import com.ardor.draganddrop.listener.DragListener
import com.ardor.draganddrop.model.SimpleModel

class SampleDragAdapter(private val listener: CustomListener) :
    ListAdapter<SimpleModel, SampleDragAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(text: SimpleModel) {
            binding.title.text = text.name
            //Set Drag and Drop
            binding.root.setOnLongClickListener { view ->
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(view)
                view?.startDragAndDrop(data, shadowBuilder, view, 0)
                false
            }
            binding.item.setOnDragListener(
                DragListener(
                    listener,
                    R.id.front_recycler_view,
                    R.id.behind_recycler_view
                )
            )
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SampleDragAdapter.ViewHolder {
        return ViewHolder(
            CardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SampleDragAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SimpleModel>() {
            override fun areItemsTheSame(oldItem: SimpleModel, newItem: SimpleModel): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: SimpleModel, newItem: SimpleModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}