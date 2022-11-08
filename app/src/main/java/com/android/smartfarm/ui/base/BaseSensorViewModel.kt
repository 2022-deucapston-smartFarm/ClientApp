package com.android.smartfarm.ui.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.smartfarm.data.entity.SensorBaseValue
import com.android.smartfarm.data.entity.SensorInfo
import com.android.smartfarm.data.repositories.Repository
import io.socket.emitter.Emitter
import org.json.JSONException
import org.json.JSONObject

open class BaseSensorViewModel(private val repository: Repository) : ViewModel() {
    private val mutableSensorBaseValue = MutableLiveData<ArrayList<HashMap<String, Double>>>()
    val sensorBaseValue get() = mutableSensorBaseValue
    private val sensorSettingListener = Emitter.Listener {
        mutableSensorBaseValue.postValue(splitSensorInfo(JSONObject(it[0].toString())))
        Log.d("test","기준값: "+it[0].toString())
    }

    fun setToReceiveBaseValue() = repository.setStartToReceiveInformation("standardOption",sensorSettingListener)

    /*private fun splitBaseValue(obj:JSONObject):SensorBaseValue{
        val sensorBaseValue=SensorBaseValue()
        sensorBaseValue.name=obj.getString("name")
        sensorBaseValue.co2=obj.getInt("co2")
        sensorBaseValue.ph=obj.getInt("ph")
        sensorBaseValue.temperature=obj.getInt("temperature")
        sensorBaseValue.illuminance=obj.getInt("illuminance")
        return sensorBaseValue
    }*/

    fun splitSensorInfo(obj: JSONObject): ArrayList<HashMap<String, Double>> {
        val item = ArrayList<HashMap<String, Double>>()
        try{
            val data = SensorInfo(obj)
            item.add(hashMapOf(Pair("temperature", data.temperature.toDouble())))
            item.add(hashMapOf(Pair("humidity", data.humidity.toDouble())))
            item.add(hashMapOf(Pair("co2", data.co2.toDouble())))
            item.add(hashMapOf(Pair("ph", data.ph)))
            item.add(hashMapOf(Pair("illuminance", data.illuminance.toDouble())))
        }catch (e: JSONException){
            val data = SensorBaseValue(obj)
            item.add(hashMapOf(Pair("temperature", data.temperature.toDouble())))
            item.add(hashMapOf(Pair("co2", data.co2.toDouble())))
            item.add(hashMapOf(Pair("ph", data.ph.toDouble())))
            item.add(hashMapOf(Pair("illuminance", data.illuminance.toDouble())))
        }
        //온도,습도,co2,ph,조도

        return item
    }
}