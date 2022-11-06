package com.android.smartfarm.data.repositories

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketIoInstance {//서버 연결 클래스
    lateinit var mSocket : Socket
    fun serverConnect(){
        try{
            var options = IO.Options()
            options.path = "/socket.io"
            options.transports = arrayOf("websocket")
            mSocket = IO.socket("http://ec2-35-78-118-67.ap-northeast-1.compute.amazonaws.com:3000",options)
            Log.d("ServerReady","OK")
        }catch (e : URISyntaxException){
            Log.d("ServerReady","Error")
        }
        try{
            mSocket.connect()
            Log.d("ServerConnect","OK")
        }catch (e : URISyntaxException){
            Log.d("ServerConnect","Error")
        }
    }

}
