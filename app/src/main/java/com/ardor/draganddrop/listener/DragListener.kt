package com.ardor.draganddrop.listener

import android.annotation.SuppressLint
import android.view.DragEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ardor.draganddrop.R
import com.ardor.draganddrop.adapter.SampleDrag2Adapter
import com.ardor.draganddrop.adapter.SampleDragAdapter
import com.ardor.draganddrop.model.SimpleModel
import java.util.*


class DragListener(
    private val topMaxSize: Int = 0,
    private val bottomMaxSize: Int = 0
) : View.OnDragListener {
    private var isDropped = false

    @SuppressLint("ResourceAsColor")
    override fun onDrag(view: View, event: DragEvent): Boolean {

        val viewSource = event.localState as View
        val targetRecyclerView: RecyclerView
        val sourceRecyclerView: RecyclerView = viewSource.parent as RecyclerView

        val topId = R.id.front_recycler_view
        val bottomId = R.id.behind_recycler_view

        if (event.action == DragEvent.ACTION_DROP) {
            isDropped = true
            val viewId = view.id
            var targetPosition = -1

            when(viewId){
                topId ->{
                    targetRecyclerView = view.rootView.findViewById(topId) as RecyclerView
                }
                bottomId->{
                    targetRecyclerView = view.rootView.findViewById(bottomId) as RecyclerView
                }
                else ->{
                    targetRecyclerView = view.parent as RecyclerView
                    targetPosition = targetRecyclerView.getChildAdapterPosition(view)
                }
            }


            val sourceAdapter =
                if (sourceRecyclerView.adapter is SampleDragAdapter) sourceRecyclerView.adapter as SampleDragAdapter? else sourceRecyclerView.adapter as SampleDrag2Adapter?
            val sourcePosition = sourceRecyclerView.getChildAdapterPosition(viewSource)

            val targetAdapter =
                if (targetRecyclerView.adapter is SampleDragAdapter) targetRecyclerView.adapter as SampleDragAdapter? else targetRecyclerView.adapter as SampleDrag2Adapter?

            //같은 Recyclerview에서 동작
            if (targetRecyclerView.id == sourceRecyclerView.id) {
                val list: MutableList<SimpleModel> = arrayListOf()
                sourceAdapter?.currentList?.let { list.addAll(it) }

                if (targetPosition >= 0) {
                    Collections.swap(list, sourcePosition, targetPosition)
                } else {
                    Collections.swap(list, sourcePosition, list.size - 1)
                }

                sourceAdapter?.submitList(list)
            } else {



                //롱클릭 한 아이템의 리사이클러뷰
                val item: SimpleModel? = sourceAdapter?.currentList?.get(sourcePosition)
                val sourceList: MutableList<SimpleModel> = arrayListOf()
                sourceAdapter?.currentList?.let { sourceList.addAll(it) }
                sourceList.removeAt(sourcePosition)
                sourceAdapter?.submitList(sourceList)

                //옮기려는 아이템의 리사이클러뷰
                val targetList: MutableList<SimpleModel> = arrayListOf()
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
                targetAdapter?.submitList(targetList)
            }
        }
        if (!isDropped && event.localState != null) {

            val cardView = event.localState as View

        }

        return true
    }
}