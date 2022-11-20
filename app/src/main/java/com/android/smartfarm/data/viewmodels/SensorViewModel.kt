package com.android.smartfarm.data.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.smartfarm.data.entity.SensorInfo
import com.android.smartfarm.data.repository.Repository
import com.android.smartfarm.ui.base.BaseSensorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.emitter.Emitter
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel

class SensorViewModel @Inject constructor(private val repository: Repository): BaseSensorViewModel(repository) {
    //센서 정보 받아서 출력
    private val mutableSensorInfo = MutableLiveData<ArrayList<HashMap<String, Double>>>()
    val sensorInfo:LiveData<ArrayList<HashMap<String, Double>>> get() = mutableSensorInfo
    private val sensorListener = Emitter.Listener { args ->
        mutableSensorInfo.postValue(splitSensorInfo(JSONObject(args[0].toString())))
    }

    fun setStartToReceiveSensorInfo() = viewModelScope.launch { repository.setStartToReceiveInformation("dumySensor",sensorListener) }

    fun splitSensorInfo(obj: JSONObject): ArrayList<HashMap<String, Double>> {
        val item = ArrayList<HashMap<String, Double>>()
        val data = SensorInfo(obj)
        item.add(hashMapOf(Pair("temperature", data.temperature.toDouble())))
        item.add(hashMapOf(Pair("humidity", data.humidity.toDouble())))
        item.add(hashMapOf(Pair("co2", data.co2.toDouble())))
        item.add(hashMapOf(Pair("ph", data.ph)))
        item.add(hashMapOf(Pair("illuminance", data.illuminance.toDouble())))

        return item
    }
}