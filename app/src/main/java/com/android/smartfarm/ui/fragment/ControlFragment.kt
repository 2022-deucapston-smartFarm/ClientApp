package com.android.smartfarm.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.smartfarm.R
import com.android.smartfarm.data.viewmodels.ControlViewModel
import com.android.smartfarm.databinding.ControlBinding
import com.android.smartfarm.ui.adapter.ControlAdapter
import com.android.smartfarm.ui.base.BindFragment
import com.android.smartfarm.ui.listener.RecyclerViewItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ControlFragment : BindFragment<ControlBinding>(R.layout.control) {
    val controlViewModel:ControlViewModel by activityViewModels()
    @Inject lateinit var controlAdapter: ControlAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.controlViewModel=controlViewModel
        binding.controlRecyclerView.adapter=controlAdapter
        binding.controlRecyclerView.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)

        controlAdapter.setOnClickListener(object :RecyclerViewItemClickListener{
            override fun onItemClickedListener(name: String, pos: Int) {
                val item = controlAdapter.getItem(pos)
                /*(when(name){
                     "ON"->
                     "OFF"->
                }*/
            }

        })
    }
}