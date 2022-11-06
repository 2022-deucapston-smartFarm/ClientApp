package com.android.smartfarm.data.repositories

import android.util.Log
import io.socket.client.Socket
import io.socket.emitter.Emitter
import okhttp3.OkHttpClient
import java.net.URISyntaxException
import javax.inject.Inject

class Repository @Inject constructor(private val socketInstance:Socket) {//서버에서 정보 받아오는 함수

    init {
        try{
            socketInstance.connect()
            Log.d("ServerConnect","OK")
        }catch (e : URISyntaxException){
            Log.d("ServerConnect","Error")
        }
    }

    fun setStartToReceiveSensorInfo(sensorListener:Emitter.Listener) {
        socketInstance.emit("sensorInfo",true)
        socketInstance.on("sensorInfo",sensorListener)
    }

    fun setSuspendToReceiveSensorInfo(){
        socketInstance.emit("sensorInfo",false)
    }
}