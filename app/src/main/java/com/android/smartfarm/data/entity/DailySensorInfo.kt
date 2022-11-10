package com.android.smartfarm.data.entity

import org.json.JSONArray
import org.json.JSONObject

data class DailySensorInfo(val name : String, val date : String, val temperature : JSONArray, val humidity : JSONArray, val co2 : JSONArray,
                           val ph : JSONArray, val illuminance : JSONArray)