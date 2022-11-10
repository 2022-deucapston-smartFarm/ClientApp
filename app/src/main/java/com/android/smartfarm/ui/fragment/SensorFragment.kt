package com.android.smartfarm.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.android.smartfarm.R
import com.android.smartfarm.data.viewmodels.SensorViewModel
import com.android.smartfarm.databinding.SensorBinding
import com.android.smartfarm.ui.adapter.SensorAdapter
import com.android.smartfarm.ui.base.BindFragment
import com.android.smartfarm.ui.listener.RecyclerViewItemClickListener
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
        sensorViewModel.setToReceiveBaseValue()

        sensorViewModel.sensorInfo.observe(viewLifecycleOwner, Observer{
            sensorAdapter.setItems(it)
            sensorAdapter.notifyDataSetChanged()
        })

        sensorViewModel.sensorBaseValue.observe(viewLifecycleOwner,Observer{
            sensorAdapter.setBaseItems(it)
            sensorAdapter.notifyDataSetChanged()
        })

        sensorAdapter.setOnItemClickedListener(object :RecyclerViewItemClickListener{
            override fun onItemClickedListener(name: String, pos: Int) {
                if(name=="습도"){
                    Toast.makeText(requireActivity(),"습도를 조절할 센서가 없어 기준값을 조정할수 없습니다.",Toast.LENGTH_SHORT).show()
                    return
                }
                val sensorSettingBaseValue = SensorSettingBaseValueDialogFragment(name)
                sensorSettingBaseValue.show(requireActivity().supportFragmentManager,"SettingBaseValueDialog")
            }
        })
    }
}