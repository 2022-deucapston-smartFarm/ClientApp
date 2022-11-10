package com.android.smartfarm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import com.android.smartfarm.data.viewmodels.SensorSettingBaseValueViewModel
import com.android.smartfarm.databinding.SensorSettingBaseValueBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SensorSettingBaseValueDialogFragment(private val name:String) :DialogFragment() {
    private var _binding:SensorSettingBaseValueBinding ?= null
    private val binding get() = _binding!!
    private lateinit var settingViewModel:SensorSettingBaseValueViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=SensorSettingBaseValueBinding.inflate(inflater,container,false)
        settingViewModel = ViewModelProvider(requireActivity())[SensorSettingBaseValueViewModel::class.java]
        settingViewModel.setToReceiveBaseValue()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.settingViewModel=settingViewModel
        binding.lifecycleOwner=this

        settingViewModel.sensorBaseValue.observe(viewLifecycleOwner,Observer{
            settingViewModel.initalize(name)
        })

        settingViewModel.mutableNavigationFlag.observe(viewLifecycleOwner,Observer{
            if(it) {
                dismiss()
                settingViewModel.mutableNavigationFlag.postValue(false)
            }
        })
    }
}