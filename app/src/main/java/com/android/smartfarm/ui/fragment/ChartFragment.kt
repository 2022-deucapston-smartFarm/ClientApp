package com.android.smartfarm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.android.smartfarm.R
import com.android.smartfarm.data.viewmodels.ChartViewModel
import com.android.smartfarm.databinding.FragmentChartBinding

class ChartFragment : Fragment() {
    private lateinit var binding : FragmentChartBinding
    private val model : ChartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_chart,
            container,
            false
        )
        binding.apply {
            chart = model
            lifecycleOwner = this@ChartFragment
        }
        return binding.root
    }

}