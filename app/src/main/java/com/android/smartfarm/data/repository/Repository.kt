package com.android.smartfarm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.android.smartfarm.data.database.NoticeDao
import com.android.smartfarm.data.entity.NoticeEntity
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.net.URISyntaxException
import javax.inject.Inject

class Repository @Inject constructor(private val socketInstance:Socket,private val noticeDao: NoticeDao) {//서버에서 정보 받아오는 함수

    init {
        try{
            socketInstance.connect()
            Log.d("ServerConnect","OK")
        }catch (e : URISyntaxException){
            Log.d("ServerConnect","Error")
        }
    }

    fun setStartToReceiveInformation(key:String,Listener:Emitter.Listener) {
        socketInstance.emit(key,true)
        socketInstance.on(key,Listener)
    }

    fun setStartToReceiveInformation(key:String,obj:String,Listener:Emitter.Listener) {
        socketInstance.emit(key,obj)
        socketInstance.on(key,Listener)
    }

    fun setChangeToReceiveInformation(key:String,info:Boolean){
        socketInstance.emit(key,info)
    }
    fun setChangeToReceiveInformation(key:String,info:Int){
        socketInstance.emit(key,info)
    }

    fun getAllNoticeData(): LiveData<List<NoticeEntity>> { return noticeDao.getAllNoticeInfo()  }

    suspend fun addNoticeInfoToDatabase(header:String,info:String){ noticeDao.addNoticeInfoToDatabase(header,info)}

    suspend fun deleteNoticeInfoToDatabase(key:Int){noticeDao.deleteNoticeInfoToDatabase(key)}
}