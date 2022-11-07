package com.android.smartfarm.data.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smartfarm.data.entity.SensorInfo
import com.android.smartfarm.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.emitter.Emitter
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel

class SensorViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    //센서 정보 받아서 출력
    private val mutableSensorInfo = MutableLiveData<ArrayList<HashMap<String, Double>>>()
    val sensorInfo get() = mutableSensorInfo
    private val sensorListener = Emitter.Listener { args ->
        Log.d("data", args[0].toString())//해당 부분에 데이터 받아온 json파일 풀어서 저장하기
        mutableSensorInfo.postValue(splitSensorInfo(JSONObject(args[0].toString())))
    }

    fun setStartToReceiveSensorInfo() =
        viewModelScope.launch { repository.setStartToReceiveInformation("dumySensor",sensorListener) }

    fun splitSensorInfo(obj: JSONObject): ArrayList<HashMap<String, Double>> {
        val item = ArrayList<HashMap<String, Double>>()
        val data = SensorInfo(obj)
        //온도,습도,co2,ph,조도

        item.add(hashMapOf(Pair("temperature", data.temperature.toDouble())))
        item.add(hashMapOf(Pair("humidity", data.humidity.toDouble())))
        item.add(hashMapOf(Pair("co2", data.co2.toDouble())))
        item.add(hashMapOf(Pair("ph", data.ph)))
        item.add(hashMapOf(Pair("illuminance", data.illuminance.toDouble())))

        return item
    }
}