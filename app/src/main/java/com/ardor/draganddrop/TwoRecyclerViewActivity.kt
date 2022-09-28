package com.ardor.draganddrop

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ardor.draganddrop.adapter.SimpleAdapter
import com.ardor.draganddrop.databinding.ActivityTwoRecyclerViewBinding
import com.ardor.draganddrop.model.SimpleModel
import com.ardor.draganddrop.viewmodel.SampleViewModel

class TwoRecyclerViewActivity : AppCompatActivity(), SimpleAdapter.OnAdapterListener {
    lateinit var binding: ActivityTwoRecyclerViewBinding
    private val viewModel: SampleViewModel by viewModels()
    private val data: List<SimpleModel> = listOf(
        SimpleModel("One", false),
        SimpleModel("Two", false),
        SimpleModel("Three", false),
        SimpleModel("Four", false),
        SimpleModel("Five", false),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_two_recycler_view)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.onAdapterListener = this

        viewModel.setData(data)
    }

    override fun onAdd(itemInfo: SimpleModel) {
        viewModel.onAdd(itemInfo)
    }

    override fun onRemove(itemInfo: SimpleModel) {
        viewModel.onRemove(itemInfo)
    }

    override fun onSet(targetIndex: Int, sourceIndex: Int, itemInfo: SimpleModel) {
        viewModel.onSet(targetIndex, sourceIndex, itemInfo)
    }

    override fun onSwap(isRed: Boolean, from: Int, to: Int) {
        viewModel.onSwap(isRed, from, to)
    }
}