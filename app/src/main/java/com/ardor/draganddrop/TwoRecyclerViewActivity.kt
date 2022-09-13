package com.ardor.draganddrop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.ardor.draganddrop.databinding.ActivityTwoRecyclerViewBinding
import com.ardor.draganddrop.model.SimpleModel
import com.ardor.draganddrop.viewmodel.SampleViewModel

class TwoRecyclerViewActivity : AppCompatActivity() {

    lateinit var binding : ActivityTwoRecyclerViewBinding
    private val viewModel : SampleViewModel by viewModels()
    private val data: List<SimpleModel> = listOf(
        SimpleModel("One"),
        SimpleModel("Two"),
        SimpleModel("Three"),
        SimpleModel("Four"),
        SimpleModel("Five"),
    )

    private val data2: List<SimpleModel> = listOf(
        SimpleModel("One", true),
        SimpleModel("Two", true),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_two_recycler_view)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.setData(data)
        viewModel.setData2(data2)
    }
}