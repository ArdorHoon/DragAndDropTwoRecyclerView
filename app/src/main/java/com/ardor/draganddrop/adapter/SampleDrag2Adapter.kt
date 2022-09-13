package com.ardor.draganddrop.adapter

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ardor.draganddrop.databinding.RedCardItemBinding
import com.ardor.draganddrop.listener.DragListener
import com.ardor.draganddrop.model.SimpleModel

class SampleDrag2Adapter : ListAdapter<SimpleModel, SampleDrag2Adapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: RedCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text: SimpleModel) {
            binding.title.text = text.name
            binding.number.text = adapterPosition.toString()
            //Set Drag and Drop
            binding.root.setOnLongClickListener { view ->
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(view)
                binding.root.tag = adapterPosition
                view?.startDragAndDrop(data, shadowBuilder, view, 0)
                false
            }
            binding.item.setOnDragListener(DragListener(3))

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleDrag2Adapter.ViewHolder {
        return ViewHolder(
            RedCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SampleDrag2Adapter.ViewHolder, position: Int) {
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