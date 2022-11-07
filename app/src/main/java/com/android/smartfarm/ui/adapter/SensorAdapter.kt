package com.android.smartfarm.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.smartfarm.databinding.SensorItemBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class SensorAdapter @Inject constructor(@ActivityContext private val context:Context) : RecyclerView.Adapter<SensorAdapter.SensorViewHolder>() {
    val items = ArrayList<HashMap<String,Double>>()
    inner class SensorViewHolder(private val binding: SensorItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:HashMap<String,Double>){
            binding.sensorItemName.text=translateSensorName(item.keys.first())
            binding.sensorItemAttr.text= item[item.keys.first()].toString()
            binding.sensorItemImg.setImageResource(context.resources.getIdentifier(item.keys.first(),"drawable",context.packageName))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        return SensorViewHolder(SensorItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items:ArrayList<HashMap<String,Double>>){
        this.items.clear()
        this.items.addAll(items)
    }

    private fun translateSensorName(name:String):String{
        var transName:String=""
        when(name){
            "temperature"-> transName = "온도"
            "humidity" -> transName = "습도"
            "co2" -> transName = "이산화탄소"
            "ph" -> transName = "ph"
            "illuminance" -> transName = "조도"
        }
        return transName
    }
}