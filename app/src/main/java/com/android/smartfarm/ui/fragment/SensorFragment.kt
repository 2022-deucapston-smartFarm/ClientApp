package com.android.smartfarm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.smartfarm.R
import com.android.smartfarm.data.viewmodels.SensorViewModel
import com.android.smartfarm.databinding.SensorFragmentBinding


class SensorFragment : Fragment() {
    private lateinit var binding : SensorFragmentBinding
    private val model : SensorViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.sensor_fragment,
            container,
            false
        )
        binding.apply {
            sensor = model
            lifecycleOwner = this@SensorFragment
        }
        return binding.root
    }
}