package com.ardor.draganddrop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ardor.draganddrop.databinding.CardItemBinding
import com.ardor.draganddrop.helper.ItemTouchHelperAdapter
import com.ardor.draganddrop.helper.OnStartDragListener
import java.util.*

class SampleTwoRecyclerviewAdapter(
    private val listener: OnStartDragListener?
): ListAdapter<String, SampleTwoRecyclerviewAdapter.ViewHolder>(diffUtil), ItemTouchHelperAdapter {

    inner class ViewHolder(val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.root.tag = adapterPosition
            binding.title.text = text
            binding.number.text = "${adapterPosition + 1}"
            binding.item.setOnLongClickListener {
                listener?.onStartDrag(this)
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SampleTwoRecyclerviewAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        val list = currentList.toMutableList()
        Collections.swap(list, fromPosition, toPosition)
        submitList(list)
        return true
    }

    override fun onItemDismiss(position: Int) {
        val list = currentList.toMutableList()
        list.removeAt(position)
        submitList(list)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }
}