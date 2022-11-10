package com.android.smartfarm.data.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smartfarm.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.emitter.Emitter
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ControlViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val mutableControlInfo = MutableLiveData<ArrayList<HashMap<String,Boolean>>>()
    val controlInfo:LiveData<ArrayList<HashMap<String,Boolean>>> get() = mutableControlInfo
    private val controlListener = Emitter.Listener {
        Log.d("control",it[0].toString())
        mutableControlInfo.postValue(splitControlInfo(JSONObject(it[0].toString())))
    }
    fun setStartToReceiveControlInfo() = viewModelScope.launch { repository.setStartToReceiveInformation("controlInfo",controlListener) }
    fun setChangeToReceiveControlInfo(key:String,info:Boolean) = viewModelScope.launch { repository.setChangeToReceiveInformation(key,info) }

    fun splitControlInfo(obj:JSONObject) : ArrayList<HashMap<String,Boolean>>{
        val items = ArrayList<HashMap<String,Boolean>>()
        items.add(hashMapOf(Pair("heat",obj.getBoolean("heat"))))
        items.add(hashMapOf(Pair("fan",obj.getBoolean("fan"))))
        items.add(hashMapOf(Pair("led",obj.getBoolean("led"))))
        items.add(hashMapOf(Pair("waterpump",obj.getBoolean("waterpump"))))
        return items
    }
}