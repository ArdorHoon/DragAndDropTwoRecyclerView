package com.ardor.draganddrop.listener

import android.view.DragEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ardor.draganddrop.adapter.DragAdapter
import com.ardor.draganddrop.model.SimpleModel
import java.util.*

abstract class DragListener(
    private val listener: ItemModifyListener,
    private val topRecyclerviewId: Int,
    private val bottomRecyclerviewId: Int,
) : View.OnDragListener {
    abstract val topMaxItemCount: Int
    abstract val bottomMaxItemCount: Int

    override fun onDrag(view: View, event: DragEvent): Boolean {
        val viewSource = event.localState as View
        val targetRecyclerView: RecyclerView
        val sourceRecyclerView: RecyclerView = viewSource.parent as RecyclerView

        if (event.action == DragEvent.ACTION_DROP) {
            val viewId = view.id
            var targetPosition = -1

            when (viewId) {
                topRecyclerviewId -> {
                    targetRecyclerView = view.rootView.findViewById(topRecyclerviewId) as RecyclerView
                }
                bottomRecyclerviewId -> {
                    targetRecyclerView = view.rootView.findViewById(bottomRecyclerviewId) as RecyclerView
                }
                else -> {
                    targetRecyclerView = view.parent as RecyclerView
                    targetPosition = targetRecyclerView.getChildAdapterPosition(view)
                }
            }

            val sourceAdapter = sourceRecyclerView.adapter as DragAdapter?
            val sourcePosition = sourceRecyclerView.getChildAdapterPosition(viewSource)
            val targetAdapter = targetRecyclerView.adapter as DragAdapter?

            if (targetRecyclerView.id == sourceRecyclerView.id) {
                val list: MutableList<SimpleModel?> = arrayListOf()
                sourceAdapter?.currentList?.let { list.addAll(it) }

                if (targetAdapter is DragAdapter) {
                    if (targetPosition >= 0 && list[targetPosition] != null) {
                        if (targetPosition >= 0) {
                            Collections.swap(list, sourcePosition, targetPosition)
                        } else {
                            Collections.swap(list, sourcePosition, list.size - 1)
                        }
                    }
                } else {
                    if (targetPosition >= 0) {
                        Collections.swap(list, sourcePosition, targetPosition)
                    } else {
                        Collections.swap(list, sourcePosition, list.size - 1)
                    }
                }

                if (sourceAdapter?.currentList?.any { it.isRed } == true) {
                    listener.setTopData(list)
                } else {
                    listener.setBottomData(list)
                }
            } else {
                if (targetAdapter?.currentList?.any { it == null } == true || targetAdapter?.currentList?.any { it.isRed } == true) {
                    if (topMaxItemCount != 0 && targetPosition in 0 until topMaxItemCount) {
                        val item: SimpleModel? =
                            sourceAdapter?.currentList?.get(sourcePosition).apply {
                                this?.isRed = true
                            }
                        val sourceList: MutableList<SimpleModel?> = arrayListOf()
                        sourceAdapter?.currentList.let {
                            if (it != null) {
                                sourceList.addAll(it)
                            }
                        }
                        sourceList.removeAt(sourcePosition)
                        listener.setBottomData(sourceList)

                        val targetList: MutableList<SimpleModel?> = arrayListOf()
                        targetAdapter.currentList.let { targetList.addAll(it) }

                        if (item != null) {
                            if (targetList[targetPosition] == null) {
                                targetList[targetPosition] = item
                            } else {
                                val temp = targetList[targetPosition].apply { this?.isRed = false }
                                sourceList.add(temp)
                                sourceAdapter?.submitList(sourceList)
                                targetList[targetPosition] = item
                            }
                        }
                        listener.setTopData(targetList)
                    }
                } else {
                    val item: SimpleModel? = sourceAdapter?.currentList?.get(sourcePosition)
                        .apply { this?.isRed = false }
                    val sourceList: MutableList<SimpleModel?> = arrayListOf()
                    sourceAdapter?.currentList?.let { sourceList.addAll(it) }
                    sourceList.removeAt(sourcePosition)
                    sourceList.add(sourcePosition, null)
                    listener.setTopData(sourceList)

                    val targetList: MutableList<SimpleModel?> = arrayListOf()
                    targetAdapter?.currentList?.let { targetList.addAll(it) }
                    if (targetPosition >= 0) {
                        if (item != null) {
                            targetList.add(targetPosition, item)
                        }
                    } else {
                        if (item != null) {
                            targetList.add(item)
                        }
                    }
                    listener.setBottomData(targetList)
                }
            }
            return true
        }
        return true
    }
}

