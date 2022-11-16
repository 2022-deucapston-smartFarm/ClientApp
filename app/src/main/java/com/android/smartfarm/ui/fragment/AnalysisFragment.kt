package com.android.smartfarm.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.android.smartfarm.R
import com.android.smartfarm.data.viewmodels.AnalysisViewModel
import com.android.smartfarm.databinding.AnalysisBinding
import com.android.smartfarm.ui.base.BindFragment
import com.borax12.materialdaterangepicker.date.DatePickerDialog
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.textView
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class AnalysisFragment : BindFragment<AnalysisBinding>(R.layout.analysis) {
    private val analysisViewModel : AnalysisViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val guildMessage = binding.lineChart.textView("화면을 꾹 눌러 기간을 지정해주세요.").apply {
            gravity=Gravity.CENTER
        }

        analysisViewModel.analysisInfo.observe(viewLifecycleOwner, Observer {
            binding.analysisChipGroup.removeAllViews()
            guildMessage.visibility = View.GONE
            for(key in it.keys){
                binding.analysisChipGroup.addView(createChip(key))
            }
        })

        analysisViewModel.categories.observe(viewLifecycleOwner,Observer{
            if (it.isNotEmpty()) {
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
                binding.lineChart.data = lineData
                setAnalysisChart(binding.lineChart)
                binding.lineChart.invalidate()
            }else{
                guildMessage.visibility = View.VISIBLE
            }
        })

        binding.lineChart.setOnLongClickListener {
            val now: Calendar = Calendar.getInstance()
            val dpd: DatePickerDialog = DatePickerDialog.newInstance(
                { view, year, monthOfYear, dayOfMonth, yearEnd, monthOfYearEnd, dayOfMonthEnd ->
                    analysisViewModel.setStartToReceiveAnalysisDayInfo(JSONObject().apply {
                        put("startDate",SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().apply { set(year,monthOfYear,dayOfMonth) }.time))
                        put("endDate",SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().apply { set(yearEnd,monthOfYearEnd,dayOfMonthEnd) }.time))
                    }.toString())
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )

            dpd.show(requireActivity().fragmentManager, "Datepickerdialog")
            true
        }
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
            isDragEnabled=true
            isDoubleTapToZoomEnabled = false
            setDrawGridBackground(false)
            description = Description().apply { text="" }
            animateY(2000, Easing.EaseInCubic)
        }
    }

}