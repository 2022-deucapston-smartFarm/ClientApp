package com.android.smartfarm.ui.fragment

import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.smartfarm.R
import com.android.smartfarm.data.entity.NoticeEntity
import com.android.smartfarm.data.viewmodels.AnalysisViewModel
import com.android.smartfarm.data.viewmodels.NoticeViewModel
import com.android.smartfarm.data.viewmodels.SensorViewModel
import com.android.smartfarm.databinding.NoticeBinding
import com.android.smartfarm.ui.adapter.NoticeAdapter
import com.android.smartfarm.ui.base.BindFragment
import dagger.hilt.android.AndroidEntryPoint
import java.lang.NullPointerException
import javax.inject.Inject

@AndroidEntryPoint
class NoticeFragment : BindFragment<NoticeBinding>(R.layout.notice) {
    private val noticeViewModel: NoticeViewModel by activityViewModels()
    private val viewmodel: AnalysisViewModel by activityViewModels()
    @Inject lateinit var noticeAdapter: NoticeAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=viewLifecycleOwner
        binding.noticeViewModel=noticeViewModel
        binding.noticeRecyclerView.adapter=noticeAdapter
        binding.noticeRecyclerView.layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)

        noticeViewModel.getAllNoticeData().observe(viewLifecycleOwner,Observer{
            noticeAdapter.setItems(it)
            noticeAdapter.notifyDataSetChanged()
        })

        if(requireActivity().intent.extras!=null){
            noticeViewModel.addNoticeInfoToDatabase(requireActivity().intent.extras!!)
            requireActivity().intent=null
        }

        binding.button.setOnClickListener {
            viewmodel.setStartToReceiveAnalysisDayInfo()
        }

    }
}