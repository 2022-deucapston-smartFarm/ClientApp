package com.android.smartfarm.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.smartfarm.databinding.ControlItemBinding
import com.android.smartfarm.ui.listener.RecyclerViewItemClickListener
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.android.synthetic.main.control_item.view.*
import javax.inject.Inject

class ControlAdapter @Inject constructor(@ActivityContext private val context: Context) : RecyclerView.Adapter<ControlAdapter.ViewHolder>(),RecyclerViewItemClickListener {
    private val items = ArrayList<String>()
    private var listener:RecyclerViewItemClickListener ?= null
    inner class ViewHolder(private val binding: ControlItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:String){
            binding.controlItemName.text=item
            binding.controlItemImg.setImageResource(context.resources.getIdentifier(item,"drawable",context.packageName))
            binding.controlButtonAdd.setOnClickListener {
                onItemClickedListener(it.controlButtonAdd.text.toString(),adapterPosition)
            }
            binding.controlButtonSub.setOnClickListener {
                onItemClickedListener(it.controlButtonSub.text.toString(),adapterPosition)
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

    fun setItems(items:ArrayList<String>){
        this.items.clear()
        this.items.addAll(items)
    }

    fun getItem(pos:Int):String{
        return items[pos]
    }

    fun setOnClickListener(listener: RecyclerViewItemClickListener){
        this.listener=listener
    }

    override fun onItemClickedListener(name:String, pos: Int) {
        this.listener?.onItemClickedListener(name,pos)
    }
}