package com.ardor.draganddrop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ardor.draganddrop.adapter.SampleAdapter
import com.ardor.draganddrop.databinding.ActivityMainBinding
import com.ardor.draganddrop.helper.MyItemTouchHelperCallback
import com.ardor.draganddrop.helper.OnStartDragListener
import com.ardor.draganddrop.viewmodel.SampleViewModel
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        binding.activity = this

        viewModel.setData(data)
    }
}