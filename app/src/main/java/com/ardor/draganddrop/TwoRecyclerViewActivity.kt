package com.ardor.draganddrop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.ardor.draganddrop.databinding.ActivityTwoRecyclerViewBinding
import com.ardor.draganddrop.viewmodel.SampleViewModel

class TwoRecyclerViewActivity : AppCompatActivity() {

    lateinit var binding : ActivityTwoRecyclerViewBinding
    private val viewModel : SampleViewModel by viewModels()
    private val data: List<String> = listOf(
        "One",
        "Two",
        "Three",
        "Four",
        "Five",
        "Six",
        "Seven",
        "Eight",
        "Nine",
        "Ten",
    )

    private val data2: List<String> = listOf(
        "One",
        "Two",
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