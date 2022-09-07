package com.ardor.draganddrop.listener

import android.util.Log
import android.view.DragEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ardor.draganddrop.R
import com.ardor.draganddrop.adapter.SampleDragAdapter


class DragListener(private val block: Boolean) : View.OnDragListener {
    private var isDropped = false

    override fun onDrag(view: View, event: DragEvent): Boolean {

        val topViewId = R.id.front_recycler_view
        val bottomViewId = R.id.behind_recycler_view

        val viewSource = event.localState as View
        val targetRecyclerView: RecyclerView = view.parent as RecyclerView
        val sourceRecyclerView: RecyclerView = viewSource.parent as RecyclerView


        if (event.action == DragEvent.ACTION_DROP) {
            isDropped = true
            var targetPosition = -1
            targetPosition = view.tag as Int


            if ((!block && targetRecyclerView.id != sourceRecyclerView.id) || block) {
                //롱클릭 한 아이템의 리사이클러뷰
                val sourceAdapter: SampleDragAdapter? =
                    sourceRecyclerView.adapter as SampleDragAdapter?
                val sourcePosition = viewSource.tag as Int
                val item: String? = sourceAdapter?.currentList?.get(sourcePosition)
                val sourceList: MutableList<String> = arrayListOf()
                sourceAdapter?.currentList?.let { sourceList.addAll(it) }
                sourceList.removeAt(sourcePosition)
                sourceAdapter?.submitList(sourceList)


                //옮기려는 아이템의 리사이클러뷰
                val targetAdapter: SampleDragAdapter? =
                    targetRecyclerView.adapter as SampleDragAdapter?
                val targetList: MutableList<String> = arrayListOf()
                targetAdapter?.currentList?.let { targetList.addAll(it) }
                if (targetPosition >= 0) {
                    targetList.add(targetPosition, item.toString())
                } else {
                    targetList.add(item.toString())
                }
                targetAdapter?.submitList(targetList)

            }
        }
        if (!isDropped && event.localState != null) {

            val cardView = event.localState as View

            when ((cardView.parent as RecyclerView).id) {
                topViewId -> {
                    Log.d("testing", "topView")
                    Log.d("testing", "${targetRecyclerView.id} target")
                    Log.d("testing", "${sourceRecyclerView.id} source")
                }

                bottomViewId -> {
                    Log.d("testing", "bottomView")
                    Log.d("testing", "${targetRecyclerView.id} target")
                    Log.d("testing", "${sourceRecyclerView.id} source")
                }
            }

        }

        return true
    }
}