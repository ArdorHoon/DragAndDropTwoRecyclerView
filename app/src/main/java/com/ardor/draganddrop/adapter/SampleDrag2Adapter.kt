package com.ardor.draganddrop.adapter

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ardor.draganddrop.R
import com.ardor.draganddrop.databinding.EmptyItemBinding
import com.ardor.draganddrop.databinding.RedCardItemBinding
import com.ardor.draganddrop.listener.CustomListener
import com.ardor.draganddrop.listener.DragListener
import com.ardor.draganddrop.model.SimpleModel

class SampleDrag2Adapter(private val listener: CustomListener) :
    ListAdapter<SimpleModel, RecyclerView.ViewHolder>(diffUtil) {

    inner class EmptyViewHolder(private val binding: EmptyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.item.setOnDragListener(
                DragListener(
                    listener,
                    R.id.front_recycler_view,
                    R.id.behind_recycler_view
                )
            )
        }
    }

    inner class ViewHolder(private val binding: RedCardItemBinding) :
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

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position] == null) EMPTY_TYPE
        else NORMAL_TYPE
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return if (viewType == EMPTY_TYPE) {
            EmptyViewHolder(
                EmptyItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            ViewHolder(
                RedCardItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == EMPTY_TYPE) {
            (holder as EmptyViewHolder).bind()
        } else {
            (holder as ViewHolder).bind(getItem(position))
        }
    }

    companion object {

        private const val NORMAL_TYPE = 1
        private const val EMPTY_TYPE = 2

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