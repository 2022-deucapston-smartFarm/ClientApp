package com.android.smartfarm.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.smartfarm.databinding.SensorItemBinding
import com.android.smartfarm.ui.listener.RecyclerViewItemClickListener
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class SensorAdapter @Inject constructor(@ActivityContext private val context:Context) : RecyclerView.Adapter<SensorAdapter.SensorViewHolder>(), RecyclerViewItemClickListener{
    private val items = ArrayList<HashMap<String,Double>>()
    private var listener:RecyclerViewItemClickListener ?= null
    private var sensorBaseValue = HashMap<String,Double>()
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

            if(sensorBaseValue.size!=0 && item.keys.first()!="humidity"){
                if(item[item.keys.first()]!! > sensorBaseValue[item.keys.first()]!!){
                    binding.sensorGuildIcon.text="기준치 이하"
                }
                else{
                    binding.sensorGuildIcon.text="기준치 초과"
                }
            }
            else if(item.keys.first()=="humidity"){
                binding.sensorGuildIcon.visibility=View.GONE
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

    fun setBaseItems(items: HashMap<String, Double>){
        sensorBaseValue.clear()
        sensorBaseValue.putAll(items)
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