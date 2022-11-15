package com.android.smartfarm.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{

    @Provides
    @Singleton
    fun providesDatabaseDao(noticeDatabase: NoticeDatabase):NoticeDao{
        return noticeDatabase.getNoticeDao()
    }

    @Provides
    @Singleton
    fun providesDatabaseInstance(@ApplicationContext context:Context):NoticeDatabase{
        return Room.databaseBuilder(context,NoticeDatabase::class.java,"NoticeDatabase").createFromAsset("database/NoticeDatabase.db").build()
    }
}