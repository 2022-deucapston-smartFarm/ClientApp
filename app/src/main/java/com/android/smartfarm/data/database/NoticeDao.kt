package com.android.smartfarm.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.android.smartfarm.data.entity.NoticeEntity

@Dao
interface NoticeDao {

    @Query("select * from NoticeEntity")
    fun getAllNoticeInfo():LiveData<List<NoticeEntity>>

    @Query("insert into NoticeEntity(header,info) values (:header,:info)")
    suspend fun addNoticeInfoToDatabase(header:String,info:String)

    @Query("delete from NoticeEntity where NoticeEntity.id = :key")
    suspend fun deleteNoticeInfoToDatabase(key:Int)
}