package com.android.smartfarm.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.android.smartfarm.R
import com.android.smartfarm.databinding.CameraBinding
import com.android.smartfarm.ui.base.BindFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraFragment : BindFragment<CameraBinding>(R.layout.camera) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webView.loadUrl("")
    }

}