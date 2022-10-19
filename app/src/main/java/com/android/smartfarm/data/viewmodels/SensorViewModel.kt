package com.android.smartfarm.data.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.smartfarm.data.entity.SensorInfo
import com.android.smartfarm.data.repositories.SocketIoInstance
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.emitter.Emitter
import javax.inject.Inject

@HiltViewModel
class SensorViewModel @Inject constructor(): ViewModel() {//센서 정보 받아서 출력

    private var getSensorInfo : Emitter.Listener
    init {
        getSensorInfo = Emitter.Listener { args->
            Log.d("sensorInfo",args[0].toString())//해당 부분에 데이터 받아온 json파일 풀어서 저장하기

        }
        SocketIoInstance.mSocket.emit("sensorInfo",true)
        SocketIoInstance.mSocket.on("sensorInfo",getSensorInfo)
    }


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