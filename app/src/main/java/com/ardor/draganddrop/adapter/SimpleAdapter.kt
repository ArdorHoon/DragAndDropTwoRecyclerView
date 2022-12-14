package com.ardor.draganddrop.adapter

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardor.draganddrop.databinding.CardItemBinding
import com.ardor.draganddrop.databinding.EmptyItemBinding
import com.ardor.draganddrop.databinding.RedCardItemBinding
import com.ardor.draganddrop.model.SimpleModel

class SimpleAdapter(
    override val isSwappable: Boolean,
    private val onAdapterListener: OnAdapterListener
) : RecyclerViewDragAdapter<SimpleModel, RecyclerView.ViewHolder>(diffUtil) {

    inner class BlueViewHolder(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(text: SimpleModel) {
            binding.title.text = text.name
            binding.root.setOnLongClickListener { view ->
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(view)
                view?.startDragAndDrop(data, shadowBuilder, view, 0)
                false
            }
            binding.item.setOnDragListener(dragListener)
        }
    }

    inner class EmptyViewHolder(private val binding: EmptyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.item.setOnDragListener(dragListener)
        }
    }

    inner class RedViewHolder(private val binding: RedCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(text: SimpleModel) {
            binding.title.text = text.name
            binding.root.setOnLongClickListener { view ->
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(view)
                view?.startDragAndDrop(data, shadowBuilder, view, 0)
                false
            }
            binding.item.setOnDragListener(dragListener)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position] == null) EMPTY_TYPE
        else if (currentList[position].isRed) RED_TYPE
        else BLUE_TYPE
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            EMPTY_TYPE -> {
                EmptyViewHolder(
                    EmptyItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            RED_TYPE -> {
                RedViewHolder(
                    RedCardItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                BlueViewHolder(
                    CardItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            EMPTY_TYPE -> {
                (holder as EmptyViewHolder).bind()
            }
            BLUE_TYPE -> {
                (holder as BlueViewHolder).bind(getItem(position))
            }
            RED_TYPE -> {
                (holder as RedViewHolder).bind(getItem(position))
            }
        }
    }

    interface OnAdapterListener {
        fun onAdd(itemInfo: SimpleModel)
        fun onRemove(itemInfo: SimpleModel)
        fun onSet(targetIndex: Int, sourceIndex: Int ,itemInfo: SimpleModel)
        fun onSwap(isRed: Boolean, from: Int, to: Int)
    }

    companion object {

        private const val BLUE_TYPE = 0
        private const val RED_TYPE = 1
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

    override fun onAdd(item: SimpleModel) {
       onAdapterListener.onAdd(item)
    }

    override fun onRemove(item: SimpleModel) {
       onAdapterListener.onRemove(item)
    }


    override fun onSwap(from: Int, to: Int) {
       if(currentList.any { it.isRed }){
           onAdapterListener.onSwap(true, from, to)
       }else{
           onAdapterListener.onSwap(false, from, to)
       }
    }

    override fun onSet(
        targetIndex: Int,
        sourceIndex: Int,
        targetItem: SimpleModel,
    ) {
        onAdapterListener.onSet(targetIndex, sourceIndex, targetItem)
    }

}