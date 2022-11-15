package com.android.smartfarm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoticeEntity(@PrimaryKey val id:Int, val header:String, val info:String) {
}