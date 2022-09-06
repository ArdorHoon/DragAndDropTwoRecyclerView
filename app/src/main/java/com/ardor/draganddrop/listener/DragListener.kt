package com.ardor.draganddrop.listener

import android.view.DragEvent
import android.view.View
import android.view.View.OnDragListener
import androidx.recyclerview.widget.RecyclerView
import com.ardor.draganddrop.R
import com.ardor.draganddrop.adapter.SampleListenerAdapter

class DragListener() : OnDragListener {
    private var isDropped = false
    override fun onDrag(view: View, event: DragEvent): Boolean {
        if (event.action == DragEvent.ACTION_DROP) {
            isDropped = true
            var positionTarget = -1
            val viewSource = event.localState as View
            val viewId = view.id
            val cvItem: Int = R.id.item
            val rvTop: Int = R.id.front_recycler_view
            val rvBottom: Int = R.id.behind_recycler_view
            when (viewId) {
                cvItem, rvTop, rvBottom -> {
                    val target: RecyclerView
                    when (viewId) {
                        rvTop -> target =
                            view.rootView.findViewById<View>(rvTop) as RecyclerView
                        rvBottom -> target =
                            view.rootView.findViewById<View>(rvBottom) as RecyclerView
                        else -> {
                            target = view.parent as RecyclerView
                            positionTarget = view.tag as Int
                        }
                    }
                    val source = viewSource.parent as RecyclerView
                    val adapterSource: SampleListenerAdapter? = source.adapter as SampleListenerAdapter?
                    val positionSource = viewSource.tag as Int
                    val sourceId = source.id

                    val list: String? = adapterSource?.let { it.currentList[positionSource]}
                    val listSource: MutableList<String> = arrayListOf()
                    adapterSource?.currentList?.let { listSource.addAll(it) }
                    listSource.removeAt(positionSource)
                    adapterSource?.submitList(listSource)

                    //타겟
                    val adapterTarget: SampleListenerAdapter? = target.adapter as SampleListenerAdapter?
                    val customListTarget: MutableList<String> = arrayListOf()
                    adapterTarget?.currentList?.let { customListTarget.addAll(it) }

                    val nList : String = list.toString()
                    if (positionTarget >= 0) {
                        customListTarget.add(positionTarget, nList)
                    } else {
                        customListTarget.add(nList)
                    }

                    adapterTarget?.submitList(customListTarget)
                }
            }
        }
        if (!isDropped && event.localState != null) {
            (event.localState as View).visibility = View.VISIBLE
        }
        return true
    }
}