package com.android.smartfarm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.android.smartfarm.R
import com.android.smartfarm.data.viewmodels.CameraViewModel
import com.android.smartfarm.databinding.FragmentCameraBinding

class CameraFragment : Fragment() {
    private lateinit var binding : FragmentCameraBinding
    private val model : CameraViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_camera,
            container,
            false
        )
        binding.apply {
            camera = model
            lifecycleOwner = this@CameraFragment
        }
        return binding.root
    }

}