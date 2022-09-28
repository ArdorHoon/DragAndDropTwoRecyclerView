package com.ardor.draganddrop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardor.draganddrop.model.SimpleModel
import java.util.*

class SampleViewModel : ViewModel() {
    private val _bottomData: MutableLiveData<List<SimpleModel?>> = MutableLiveData()
    val bottomData: LiveData<List<SimpleModel?>> = _bottomData

    var tempData: MutableList<SimpleModel?> =
        arrayListOf(SimpleModel("one", true), null, null)

    private val _topData: MutableLiveData<List<SimpleModel?>> = MutableLiveData()
    val topData: LiveData<List<SimpleModel?>> = _topData

    fun setData(items: List<SimpleModel>) {
        _bottomData.value = items
    }

    fun setTopData(list: MutableList<SimpleModel?>) {
        _topData.value = list
    }

    fun setBottomData(list: MutableList<SimpleModel?>) {
        _bottomData.value = list
    }

    fun onSet(targetIndex: Int, sourceIndex: Int, targetItem: SimpleModel) {
        val topTempData = _topData.value?.toMutableList()
        val bottomTempData = _bottomData.value?.toMutableList()

        if (targetItem.isRed) {
            val item = bottomTempData?.get(sourceIndex)?.copy(isRed = true)
            topTempData?.let {
                it[targetIndex] = item
            }

            bottomTempData?.let {
                it[sourceIndex] = targetItem.copy(isRed = false)
            }

        } else {
            val item = topTempData?.get(targetIndex)?.copy(isRed = false)
            topTempData?.let {
                it[targetIndex] = targetItem.copy(isRed = true)
            }

            bottomTempData?.let {
                it[sourceIndex] = item
            }
        }
        _bottomData.value = bottomTempData?.toList()
        _topData.value = topTempData?.toList()
    }

    fun onRemove(item: SimpleModel) {
        if (item.isRed) {
            _topData.value?.let { topData ->
                val topTempData = topData.toMutableList()
                val bottomTempData = _bottomData.value?.toMutableList()
                for (i in topTempData.indices) {
                    if (topTempData[i] == item) {
                        topTempData[i] = null
                        break
                    }
                }
                _topData.value = topTempData.toList()
                bottomTempData?.add(item.copy(isRed = false))
                _bottomData.value = bottomTempData?.toList()
            }
        } else {
            _bottomData.value?.let { bottomData ->
                val topTempData = _topData.value?.toMutableList()
                val bottomTempData = bottomData.toMutableList()
                bottomTempData.remove(item)
                _bottomData.value = bottomTempData.toList()
                for (i in topTempData?.indices!!) {
                    if (topTempData[i] == null) {
                        topTempData[i] = item.copy(isRed = true)
                        break
                    }
                }
                topTempData.sortWith(Comparator.nullsLast(null))
                _topData.value = topTempData.toList()
            }
        }
    }

    fun onAdd(item: SimpleModel) {
        if (!item.isRed) {
            _topData.value?.let { topData ->
                val topTempData = topData.toMutableList()
                val bottomTempData = _bottomData.value?.toMutableList()

                if (topTempData.filterNotNull().size < TOP_DATA_MAX_SIZE) {
                    for (i in topTempData.indices) {
                        if (topTempData[i] == null) {
                            topTempData[i] = item.copy(isRed = true)
                            break
                        }
                    }
                    _topData.value = topTempData.toList()
                    bottomTempData?.remove(item)
                    _bottomData.value = bottomTempData?.toList()
                }
            }
        } else {
            _bottomData.value?.let { bottomData ->
                val topTempData = _topData.value?.toMutableList()
                val bottomTempData = bottomData.toMutableList()

                bottomTempData.add(item.copy(isRed = false))
                _bottomData.value = bottomTempData.toList()
                topTempData?.remove(item)
                topTempData?.add(null)
                topTempData?.sortWith(Comparator.nullsLast(null))
                _topData.value = topTempData?.toList()
            }
        }
    }

    fun onSwap(isRed: Boolean, from: Int, to: Int) {
        if (isRed) {
            _topData.value?.let {
                val data = it.toMutableList()
                Collections.swap(data, from, to)
                _topData.value = data.toList()
            }
        } else {
            _bottomData.value?.let {
                val data = it.toMutableList()
                Collections.swap(data, from, to)
                _bottomData.value = data.toList()
            }
        }
    }

    init {
        _topData.value = tempData
    }

    companion object {
        const val TOP_DATA_MAX_SIZE = 3
    }
}