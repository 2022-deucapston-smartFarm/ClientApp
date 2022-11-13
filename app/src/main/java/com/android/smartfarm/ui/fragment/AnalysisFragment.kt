package com.android.smartfarm.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.android.smartfarm.R
import com.android.smartfarm.data.viewmodels.AnalysisViewModel
import com.android.smartfarm.databinding.AnalysisBinding
import com.android.smartfarm.ui.base.BindFragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.custom.style

@AndroidEntryPoint
class AnalysisFragment : BindFragment<AnalysisBinding>(R.layout.analysis) {
    private val analysisViewModel : AnalysisViewModel by activityViewModels()
    private val analysisSharedPref : SharedPreferences by lazy { requireActivity().getSharedPreferences("분석데이터_분류_카테고리", Context.MODE_PRIVATE) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        analysisViewModel.setStartToReceiveAnalysisDayInfo()
        analysisViewModel.analysisInfo.observe(viewLifecycleOwner, Observer {
            binding.analysisChipGroup.removeAllViews()
            for(key in it.keys){
                binding.analysisChipGroup.addView(createChip(key))
            }
        })

        analysisViewModel.categories.observe(viewLifecycleOwner,Observer{
            if (it != null) {
                val lineData = LineData()
                for(category in it){
                    lineData.addDataSet(analysisViewModel.createLineDataSet(
                        category,
                        2F,
                        6F,
                        Color.parseColor("#FFA1B4DC"),
                        Color.BLUE,
                        Color.parseColor("#FFA1B4DC")))
                }
                binding.pieChart.data = lineData
                setAnalysisChart(binding.pieChart)
                binding.pieChart.invalidate()
            }

        })
    }

    private fun createChip(key:String) : Chip {
        val chip = Chip(requireActivity()).apply {
            text = key
            isCheckable = true
            setOnCheckedChangeListener { p0, p1 ->
                if (p1) {
                    val data = analysisViewModel.mutableCategories.value
                    analysisViewModel.mutableCategories.value = data?.apply {add(p0.text.toString())}
                } else {
                    val data = analysisViewModel.mutableCategories.value
                    analysisViewModel.mutableCategories.value = data?.apply { remove(p0.text.toString()) }
                }
            }
        }
        return chip
    }

    private fun setAnalysisChart(lineChart:LineChart){
        val xAxis = lineChart.xAxis
        xAxis.setDrawLabels(true)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.BLACK
        xAxis.textSize = 13F
        xAxis.granularity= 2f
        xAxis.spaceMax=1f
        xAxis.isGranularityEnabled = true
        xAxis.enableGridDashedLine(10F, 24F, 0F)

        val lAxis = lineChart.axisLeft
        lAxis.textColor = Color.BLACK
        lAxis.textSize = 13F


        val rAxis = lineChart.axisRight
        rAxis.setDrawLabels(false)
        rAxis.setDrawAxisLine(false)
        rAxis.setDrawGridLines(false)


        lineChart.apply{
            setVisibleXRange(10F,10F)
            isDragXEnabled=true
            isDoubleTapToZoomEnabled = true
            setDrawGridBackground(false)
            description = Description().apply { text="" }
            animateY(2000, Easing.EaseInCubic)
        }
    }


}