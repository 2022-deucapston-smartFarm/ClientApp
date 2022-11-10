package com.android.smartfarm.data.entity

import androidx.annotation.LayoutRes
import androidx.annotation.RawRes
import androidx.databinding.ObservableField

data class SensorSettingEntity(val settingName:ObservableField<String>,val settingPresentValue:ObservableField<String>, val settingHint:ObservableField<String>, @RawRes var settingImgRes:ObservableField<Int>, val settingInput:ObservableField<String>){
    constructor():this(ObservableField(""),ObservableField(""),ObservableField(""),ObservableField(0),ObservableField(""))
}