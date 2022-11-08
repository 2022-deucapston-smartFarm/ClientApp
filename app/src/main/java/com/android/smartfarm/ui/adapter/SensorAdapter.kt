package com.android.smartfarm.ui.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.smartfarm.data.entity.SensorBaseValue
import com.android.smartfarm.databinding.SensorItemBinding
import com.android.smartfarm.ui.listener.RecyclerViewItemClickListener
import dagger.hilt.android.qualifiers.ActivityContext
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.backgroundColorResource
import javax.inject.Inject

class SensorAdapter @Inject constructor(@ActivityContext private val context:Context) : RecyclerView.Adapter<SensorAdapter.SensorViewHolder>(), RecyclerViewItemClickListener{
    private val items = ArrayList<HashMap<String,Double>>()
    private var listener:RecyclerViewItemClickListener ?= null
    private var sensorBaseValue = ArrayList<HashMap<String,Double>>()
    inner class SensorViewHolder(private val binding: SensorItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HashMap<String, Double>) {
            binding.sensorItemName.text = translateSensorName(item.keys.first())
            binding.sensorItemAttr.text = item[item.keys.first()].toString()
            binding.sensorItemImg.setImageResource(
                context.resources.getIdentifier(
                    item.keys.first(),
                    "drawable",
                    context.packageName
                )
            )
            binding.sensorItemImg.setOnClickListener {
                onItemClickedListener(binding.sensorItemName.text.toString(), adapterPosition)
            }
            if(sensorBaseValue.size!=0){
                when(translateSensorName(item.keys.first())){
                    "온도"->{
                        if(item[item.keys.first()]!! > sensorBaseValue[0]["temperature"]!!){
                            binding.sensorGuildIcon.text="양호"
                            binding.sensorGuildIcon.backgroundColor= Color.BLUE
                        }
                        else{
                            binding.sensorGuildIcon.text="위험"
                            binding.sensorGuildIcon.backgroundColor= Color.RED
                        }
                    }
                    "이산화탄소"->{
                        if(item[item.keys.first()]!! > sensorBaseValue[1]["co2"]!!){
                            binding.sensorGuildIcon.text="양호"
                            binding.sensorGuildIcon.backgroundColor= Color.BLUE
                        }
                        else{
                            binding.sensorGuildIcon.text="위험"
                            binding.sensorGuildIcon.backgroundColor= Color.RED
                        }
                    }
                    "ph"->{
                        if(item[item.keys.first()]!! > sensorBaseValue[2]["ph"]!!){
                            binding.sensorGuildIcon.text="양호"
                            binding.sensorGuildIcon.backgroundColor= Color.BLUE
                        }
                        else{
                            binding.sensorGuildIcon.text="위험"
                            binding.sensorGuildIcon.backgroundColor= Color.RED
                        }
                    }
                    "조도"->{
                        if(item[item.keys.first()]!! > sensorBaseValue[3]["illuminance"]!!){
                            binding.sensorGuildIcon.text="양호"
                            binding.sensorGuildIcon.backgroundColor= Color.BLUE
                        }
                        else{
                            binding.sensorGuildIcon.text="위험"
                            binding.sensorGuildIcon.backgroundColor= Color.RED
                        }
                    }
                }
            }

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

    fun setBaseItems(items: ArrayList<HashMap<String, Double>>){
        sensorBaseValue.clear()
        sensorBaseValue.addAll(items)
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

    override fun onItemClickedListener(name: String, pos: Int) {
        listener?.onItemClickedListener(name,pos)
    }

    fun setOnItemClickedListener(listener:RecyclerViewItemClickListener){
        this.listener=listener
    }
}