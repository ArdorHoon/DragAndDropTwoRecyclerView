package com.ardor.draganddrop.adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ardor.draganddrop.databinding.CardItemBinding
import android.view.View.DragShadowBuilder
import com.ardor.draganddrop.listener.DragListener

class SampleListenerAdapter(
) : ListAdapter<String, SampleListenerAdapter.ViewHolder>(diffUtil), View.OnTouchListener {

    inner class ViewHolder(val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String) {
            binding.root.tag = adapterPosition
            binding.title.text = text
            binding.number.text = "${adapterPosition + 1}"
            binding.root.setOnTouchListener(this@SampleListenerAdapter)
            binding.root.setOnDragListener(DragListener())
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

    override fun onBindViewHolder(holder: SampleListenerAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val data = ClipData.newPlainText("", "")
            val shadowBuilder = DragShadowBuilder(view)
            view?.startDragAndDrop(data, shadowBuilder, view,0)
        }
        return false
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