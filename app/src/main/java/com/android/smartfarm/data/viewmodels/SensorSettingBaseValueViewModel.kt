package com.android.smartfarm.data.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.smartfarm.data.entity.SensorSettingEntity
import com.android.smartfarm.data.repository.Repository
import com.android.smartfarm.ui.base.BaseSensorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SensorSettingBaseValueViewModel @Inject constructor(@ApplicationContext private val context:Context,private val repository: Repository)
    :BaseSensorViewModel(repository){
    val settingBaseValue by lazy{ SensorSettingEntity() }
    val mutableNavigationFlag = MutableLiveData<Boolean>(false)

    fun initalize(name:String){
        if(sensorBaseValue.value!=null){
            when(name){
                "온도"->settingBaseValue.settingPresentValue.set("현재기준값 : "+sensorBaseValue.value!!["temperature"].toString())
                "이산화탄소"->settingBaseValue.settingPresentValue.set("현재기준값 : "+sensorBaseValue.value!!["co2"].toString())
                "ph"->settingBaseValue.settingPresentValue.set("현재기준값 : "+sensorBaseValue.value!!["ph"].toString())
                "조도"->settingBaseValue.settingPresentValue.set("현재기준값 : "+sensorBaseValue.value!!["illuminance"].toString())
            }
            settingBaseValue.settingName.set(name)
            settingBaseValue.settingImgRes.set(context.resources.getIdentifier(translateSensorName(name),"drawable",context.packageName))
            settingBaseValue.settingHint.set("$name 입력")
        }
    }

    fun setChangeToReceiveBaseValue() = viewModelScope.launch {
        repository.setChangeToReceiveInformation("standard"+translateSensorName(settingBaseValue.settingName.get()!!).substring(0,1).uppercase()
                + translateSensorName(settingBaseValue.settingName.get()!!).substring(1)
        ,settingBaseValue.settingInput.get()!!.toInt())
        settingBaseValue.settingInput.set("")
        cancelDialog()
    }

    fun cancelDialog(){
        mutableNavigationFlag.postValue(true)
    }

    private fun translateSensorName(name:String):String{
        var transName:String=""
        when(name){
            "온도"-> transName = "temperature"
            "습도" -> transName = "humidity"
            "이산화탄소" -> transName = "co2"
            "ph" -> transName = "ph"
            "조도" -> transName = "illuminance"
        }
        return transName
    }
}