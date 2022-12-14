package com.android.smartfarm.data.viewmodels

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smartfarm.data.entity.NoticeEntity
import com.android.smartfarm.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.emitter.Emitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    val listener = Emitter.Listener {

    }
    fun getAllNoticeData(): LiveData<List<NoticeEntity>> {
        return repository.getAllNoticeData()
    }

    fun addNoticeInfoToDatabase(bundle:Bundle){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNoticeInfoToDatabase(bundle.get("header").toString(),bundle.get("info").toString()) }
    }

    fun deleteNoticeInfoToDatabase(item:NoticeEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNoticeInfoToDatabase(item.id)
        }
    }
}