package com.android.smartfarm.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.android.smartfarm.R
import com.android.smartfarm.data.viewmodels.AnalysisViewModel
import com.android.smartfarm.databinding.AnalysisBinding
import com.android.smartfarm.ui.base.BindFragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnalysisFragment : BindFragment<AnalysisBinding>(R.layout.analysis) {
    private val analysisViewModel : AnalysisViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        analysisViewModel.setStartToReceiveControlInfo()
        setAnalysisChart(binding.pieChart)
        analysisViewModel.analysisInfo.observe(viewLifecycleOwner, Observer {
            binding.analysisSpinner.adapter= ArrayAdapter<String>(requireActivity(), R.layout.support_simple_spinner_dropdown_item, // 보스타입지정 spinner
                ArrayList<String>().apply {
                    for(i in it.keys){
                        add(i)
                    }})
            binding.analysisSpinner.setSelection(0)

            binding.analysisSpinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val lineData = LineData()
                    lineData.addDataSet(analysisViewModel.createLineDataSet(it,
                        p0!!.selectedItem.toString(),
                        2F,
                        6F,
                        Color.parseColor("#FFA1B4DC"),
                        Color.BLUE,
                        Color.parseColor("#FFA1B4DC")))
                    binding.pieChart.data = lineData
                    binding.pieChart.invalidate()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

        })
    }

    fun setAnalysisChart(lineChart:LineChart){
        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.BLACK
        xAxis.enableGridDashedLine(8F, 24F, 0F)

        val yLAxis = lineChart.axisLeft
        yLAxis.textColor = Color.BLACK

        val yRAxis = lineChart.axisRight
        yRAxis.setDrawLabels(false)
        yRAxis.setDrawAxisLine(false)
        yRAxis.setDrawGridLines(false)

        val description = Description()
        description.text = ""

        lineChart.setVisibleXRangeMinimum(24F)
        lineChart.isDoubleTapToZoomEnabled = false
        lineChart.setDrawGridBackground(false)
        lineChart.description = description
        lineChart.animateY(2000, Easing.EaseInCubic)
        lineChart.invalidate()
    }


}