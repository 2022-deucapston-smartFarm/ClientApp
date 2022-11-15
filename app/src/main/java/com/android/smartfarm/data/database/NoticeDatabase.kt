package com.android.smartfarm.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.smartfarm.data.entity.NoticeEntity

@Database(entities = [NoticeEntity::class], version = 1, exportSchema = true)
abstract class NoticeDatabase :RoomDatabase(){
    abstract fun getNoticeDao():NoticeDao
}