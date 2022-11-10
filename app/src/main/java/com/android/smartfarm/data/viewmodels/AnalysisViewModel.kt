package com.android.smartfarm.data.viewmodels

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smartfarm.data.entity.DailySensorInfo
import com.android.smartfarm.data.repositories.Repository
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.emitter.Emitter
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor(private val repository: Repository) : ViewModel() { //통계 정보 받아와서 저장,출력
    private val mutableAnalysisInfo = MutableLiveData<HashMap<String,ArrayList<Double>>>()
    val analysisInfo:LiveData<HashMap<String,ArrayList<Double>>> get() = mutableAnalysisInfo
    private val analysisListener = Emitter.Listener {
        mutableAnalysisInfo.postValue(splitAnalysisInfo(JSONObject(it[0].toString())))
    }
    fun setStartToReceiveControlInfo() = viewModelScope.launch { repository.setStartToReceiveInformation("dumyDaily",analysisListener) }

    private fun splitAnalysisInfo(obj:JSONObject):HashMap<String,ArrayList<Double>>{
        val items=HashMap<String,ArrayList<Double>>()
        items["temperature"] = convertJsonArrayToArrayList(obj.getJSONArray("temperature"))
        items["humidity"] = convertJsonArrayToArrayList(obj.getJSONArray("humidity"))
        items["co2"] = convertJsonArrayToArrayList(obj.getJSONArray("co2"))
        items["ph"] = convertJsonArrayToArrayList(obj.getJSONArray("ph"))
        items["illuminance"] = convertJsonArrayToArrayList(obj.getJSONArray("illuminance"))
        return items
    }

    private fun convertJsonArrayToArrayList(obj:JSONArray):ArrayList<Double>{
        val temp = ArrayList<Double>()
        for(i in 0 until obj.length()){
            temp.add(obj.getDouble(i))
        }
        return temp
    }

    fun createLineDataSet(item:HashMap<String,ArrayList<Double>>,label:String,linewidth:Float,
                          circleRadius:Float,circleColor:Int,circleHoleColor:Int,color: Int): LineDataSet {
        val entries = ArrayList<Entry>()
        var counter = 1
        for(i in item[label]!!){
            entries.add(Entry(counter++.toFloat(), i.toFloat()))
        }

        val lineDataSet = LineDataSet(entries, label)
        lineDataSet.lineWidth = linewidth
        lineDataSet.circleRadius = circleRadius
        lineDataSet.setCircleColor(circleColor)
        lineDataSet.circleHoleColor=circleHoleColor
        lineDataSet.color = color
        lineDataSet.setDrawCircleHole(true)
        lineDataSet.setDrawCircles(true)
        lineDataSet.setDrawHorizontalHighlightIndicator(false)
        lineDataSet.setDrawHighlightIndicators(false)
        lineDataSet.setDrawValues(false)
        return lineDataSet
    }

}