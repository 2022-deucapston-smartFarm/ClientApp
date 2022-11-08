package com.android.smartfarm.data.entity

import org.json.JSONObject

data class SensorBaseValue(var name : String, var temperature : Int, var co2 : Int, var ph : Int, var illuminance : Int){
    constructor():this("",0,0,0,0)
    constructor(obj: JSONObject):this(obj.getString("name"),
        obj.getInt("temperature"),
        obj.getInt("co2"),
        obj.getInt("ph"),
        obj.getInt("illuminance"))
}