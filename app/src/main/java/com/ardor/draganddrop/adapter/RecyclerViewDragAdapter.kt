package com.ardor.draganddrop.adapter

import android.view.DragEvent
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewDragAdapter<T, VH : RecyclerView.ViewHolder>(
    diffUtil: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffUtil) {

    abstract val isSwappable: Boolean
    val dragListener = object : View.OnDragListener {
        override fun onDrag(view: View?, event: DragEvent?): Boolean {
            event?.let {
                if (it.action == DragEvent.ACTION_DROP) {
                    val sourceView = it.localState as View
                    val sourceRecyclerView = sourceView.parent as RecyclerView
                    val sourceAdapter = sourceRecyclerView.adapter as RecyclerViewDragAdapter<T, VH>
                    val sourcePosition = sourceRecyclerView.getChildAdapterPosition(sourceView)
                    view?.let { targetView ->
                        var targetRecyclerView: RecyclerView? = targetView as? RecyclerView
                        if (targetRecyclerView == null) {
                            targetRecyclerView = targetView.parent as? RecyclerView
                        }
                        if (targetRecyclerView !is RecyclerView) {
                            return false
                        }
                        val targetAdapter =
                            targetRecyclerView.adapter as RecyclerViewDragAdapter<T, VH>
                        val targetPosition = if (targetView is RecyclerView) {
                            targetAdapter.currentList.size
                        } else {
                            targetRecyclerView.getChildAdapterPosition(targetView)
                        }
                        //if same Recyclerview swap two item
                        if (sourceRecyclerView.id == targetRecyclerView.id) {
                            if (isSwappable) {
                                if (targetPosition >= 0 && sourceAdapter.currentList[targetPosition] != null) {
                                    if (targetPosition >= 0) {
                                        sourceAdapter.onSwap(sourcePosition, targetPosition)
                                    } else {
                                        sourceAdapter.onSwap(
                                            sourcePosition,
                                            sourceAdapter.currentList.size - 1
                                        )
                                    }
                                }
                            }
                        } else {
                            try {
                                targetAdapter.currentList[targetPosition]?.let {
                                    if (sourceAdapter.currentList.size < targetPosition) {
                                        //개수 적은 데에서 많은 데로
                                        sourceAdapter.onSet(
                                            sourcePosition,
                                            targetAdapter.currentList[targetPosition]
                                        )
                                    } else {
                                        //개수 많은 데에서 적은 대로
                                        targetAdapter.onSet(
                                            targetPosition,
                                            sourceAdapter.currentList[sourcePosition]
                                        )
                                    }
                                } ?: run {
                                    //recyclerview에 아이템이 없는 곳에 드래그 앤 드랍
                                    targetAdapter.onAdd(sourceAdapter.currentList[sourcePosition])
                                }
                            } catch (e: IndexOutOfBoundsException) {
                                sourceAdapter.onRemove(sourceAdapter.currentList[sourcePosition])
                            }
                        }
                    } ?: run {
                        return false
                    }
                }
            }
            return true
        }
    }

    abstract fun onAdd(item: T)

    abstract fun onRemove(item: T)

    abstract fun onSet(index: Int, item: T)

    abstract fun onSwap(from: Int, to: Int)

}