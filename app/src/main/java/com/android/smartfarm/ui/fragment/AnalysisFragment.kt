package com.android.smartfarm.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.android.smartfarm.R
import com.android.smartfarm.data.viewmodels.ChartViewModel
import com.android.smartfarm.databinding.AnalysisBinding
import com.android.smartfarm.ui.base.BindFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalysisFragment : BindFragment<AnalysisBinding>(R.layout.analysis) {
    private val chartViewModel : ChartViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}