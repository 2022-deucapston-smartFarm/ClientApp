package com.android.smartfarm.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView
import com.android.smartfarm.R
import com.android.smartfarm.databinding.ControlItemBinding
import com.android.smartfarm.ui.listener.RecyclerViewItemClickListener
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ControlAdapter @Inject constructor(@ActivityContext private val context: Context) : RecyclerView.Adapter<ControlAdapter.ViewHolder>(),RecyclerViewItemClickListener {
    private val items = ArrayList<HashMap<String,Boolean>>()
    private var listener:RecyclerViewItemClickListener ?= null
    inner class ViewHolder(private val binding: ControlItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:HashMap<String,Boolean>){
            binding.controlItemName.text=translateControlName(item.keys.first())
            binding.controlItemImg.setImageResource(context.resources.getIdentifier(item.keys.first(),"drawable",context.packageName))
            if(item[item.keys.first()]!!){
                binding.controlButtonAdd.setStrokeColorResource(R.color.online)
                binding.controlButtonSub.setStrokeColorResource(R.color.black)
            }
            else{
                binding.controlButtonSub.setStrokeColorResource(R.color.online)
                binding.controlButtonAdd.setStrokeColorResource(R.color.black)
            }
            binding.controlButtonAdd.setOnClickListener {
                onItemClickedListener(binding.controlButtonAdd.text.toString(),adapterPosition)
            }
            binding.controlButtonSub.setOnClickListener {
                onItemClickedListener(binding.controlButtonSub.text.toString(),adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ControlItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items:ArrayList<HashMap<String,Boolean>>){
        this.items.clear()
        this.items.addAll(items)
    }

    fun getItem(pos:Int):HashMap<String,Boolean>{
        return items[pos]
    }

    private fun translateControlName(name:String) : String{
        var transName=""
        when(name){
            "heat" -> transName="히터"
            "fan" -> transName = "팬"
            "led" -> transName = "LED"
            "waterpump" -> transName = "워터펌프"
        }
        return transName
    }

    fun setOnClickListener(listener: RecyclerViewItemClickListener){
        this.listener=listener
    }

    override fun onItemClickedListener(name:String, pos: Int) {
        this.listener?.onItemClickedListener(name,pos)
    }
}