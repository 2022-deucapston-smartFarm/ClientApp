package com.android.smartfarm.data.entity

import org.json.JSONObject

data class SensorInfo(val name: String,val date: String,val temperature: Int,val humidity: Int,val co2: Int,val ph: Double,val illuminance: Int){
    constructor(obj:JSONObject):this(obj.getString("name"),
        obj.getString("date"),
        obj.getInt("temperature"),
        obj.getInt("humidity"),
        obj.getInt("co2"),
        obj.getDouble("ph"),
        obj.getInt("illuminance"))
}
