package com.ardor.draganddrop.listener

import android.util.Log
import android.view.DragEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ardor.draganddrop.adapter.SampleDrag2Adapter
import com.ardor.draganddrop.adapter.SampleDragAdapter
import com.ardor.draganddrop.model.SimpleModel
import java.util.*

class DragListener(
    private val listener: CustomListener,
    private val topId: Int,
    private val bottomId: Int,
) : View.OnDragListener {
    private var isDropped = false

    override fun onDrag(view: View, event: DragEvent): Boolean {

        val viewSource = event.localState as View
        val targetRecyclerView: RecyclerView
        val sourceRecyclerView: RecyclerView = viewSource.parent as RecyclerView

        if (event.action == DragEvent.ACTION_DROP) {
            isDropped = true
            val viewId = view.id
            var targetPosition = -1

            when (viewId) {
                topId -> {
                    targetRecyclerView = view.rootView.findViewById(topId) as RecyclerView
                }
                bottomId -> {
                    targetRecyclerView = view.rootView.findViewById(bottomId) as RecyclerView
                }
                else -> {
                    targetRecyclerView = view.parent as RecyclerView
                    targetPosition = targetRecyclerView.getChildAdapterPosition(view)
                }
            }

            val sourceAdapter =
                if (sourceRecyclerView.adapter is SampleDragAdapter) sourceRecyclerView.adapter as SampleDragAdapter? else sourceRecyclerView.adapter as SampleDrag2Adapter?
            val sourcePosition = sourceRecyclerView.getChildAdapterPosition(viewSource)

            val targetAdapter =
                if (targetRecyclerView.adapter is SampleDragAdapter) targetRecyclerView.adapter as SampleDragAdapter? else targetRecyclerView.adapter as SampleDrag2Adapter?

            if (targetRecyclerView.id == sourceRecyclerView.id) {

                val list: MutableList<SimpleModel?> = arrayListOf()
                sourceAdapter?.currentList?.let { list.addAll(it) }

                if (targetAdapter is SampleDrag2Adapter) {

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
                if (sourceAdapter is SampleDrag2Adapter) {
                    listener.setTopData(list)
                } else {
                    listener.setBottomData(list)
                }
            } else {
                if (targetAdapter is SampleDrag2Adapter) {
                    if (targetPosition >= 0 && targetPosition < listener.getMaxSize()) {
                        val item: SimpleModel? = sourceAdapter?.currentList?.get(sourcePosition)
                        val sourceList: MutableList<SimpleModel?> = arrayListOf()
                        sourceAdapter?.currentList?.let { sourceList.addAll(it) }
                        sourceList.removeAt(sourcePosition)
                        listener.setBottomData(sourceList)

                        val targetList: MutableList<SimpleModel?> = arrayListOf()
                        targetAdapter.currentList.let { targetList.addAll(it) }

                        if (item != null) {
                            if (targetList[targetPosition] == null) {
                                targetList[targetPosition] = item
                            } else {
                                val temp = targetList[targetPosition]
                                sourceList.add(temp)
                                sourceAdapter.submitList(sourceList)
                                targetList[targetPosition] = item
                            }
                        }
                        listener.setTopData(targetList)
                    }
                } else {
                    val item: SimpleModel? = sourceAdapter?.currentList?.get(sourcePosition)
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