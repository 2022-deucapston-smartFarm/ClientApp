package com.android.smartfarm.ui.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.smartfarm.data.entity.SensorBaseValue
import com.android.smartfarm.data.repository.Repository
import io.socket.emitter.Emitter
import org.json.JSONObject

open class BaseSensorViewModel(private val repository: Repository) : ViewModel() {
    private val mutableSensorBaseValue = MutableLiveData<HashMap<String, Double>>()
    val sensorBaseValue:LiveData<HashMap<String, Double>> get() = mutableSensorBaseValue
    private val sensorSettingListener = Emitter.Listener {
        mutableSensorBaseValue.postValue(splitBaseValue(JSONObject(it[0].toString())))
        Log.d("test","기준값: "+it[0].toString())
    }

    fun setToReceiveBaseValue() = repository.setStartToReceiveInformation("standardOption",sensorSettingListener)

    private fun splitBaseValue(obj:JSONObject):HashMap<String,Double>{
        val item = HashMap<String, Double>()
        val data = SensorBaseValue(obj)
        item["temperature"] = data.temperature.toDouble()
        item["co2"] = data.co2.toDouble()
        item["ph"] = data.ph.toDouble()
        item["illuminance"] = data.illuminance.toDouble()
        return item
    }
}