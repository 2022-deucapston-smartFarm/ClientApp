package com.android.smartfarm.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.android.smartfarm.R
import com.android.smartfarm.data.viewmodels.SensorViewModel
import com.android.smartfarm.databinding.SensorBinding
import com.android.smartfarm.ui.adapter.SensorAdapter
import com.android.smartfarm.ui.base.BindFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SensorFragment : BindFragment<SensorBinding>(R.layout.sensor) {
    private val sensorViewModel : SensorViewModel by activityViewModels()
    @Inject lateinit var sensorAdapter:SensorAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sensorViewModel=sensorViewModel
        binding.sensorRecyclerView.adapter=sensorAdapter
        binding.sensorRecyclerView.layoutManager=GridLayoutManager(requireActivity(),2,GridLayoutManager.VERTICAL,false)
        sensorViewModel.setStartToReceiveSensorInfo()

        sensorViewModel.sensorInfo.observe(viewLifecycleOwner, Observer{
            sensorAdapter.setItems(it)
            sensorAdapter.notifyDataSetChanged()
        })

    }
}