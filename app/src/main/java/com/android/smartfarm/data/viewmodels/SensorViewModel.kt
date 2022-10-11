package com.android.smartfarm.data.viewmodels

import androidx.lifecycle.ViewModel
import com.android.smartfarm.data.entity.SensorInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SensorViewModel @Inject constructor(): ViewModel() {//센서 정보 받아서 출력

    fun splitSensorInfo(sensorInfo: SensorInfo):ArrayList<HashMap<String,Double>>{
        val item=ArrayList<HashMap<String,Double>>()

        //온도,습도,co2,ph,조도
        item[0]["temperature"]=sensorInfo.temperature.toDouble()
        item[1]["humidity"]=sensorInfo.humidity.toDouble()
        item[2]["co2"]=sensorInfo.co2.toDouble()
        item[3]["ph"]=sensorInfo.ph
        item[4]["illuminance"]=sensorInfo.illuminance.toDouble()

        return item
    }
}