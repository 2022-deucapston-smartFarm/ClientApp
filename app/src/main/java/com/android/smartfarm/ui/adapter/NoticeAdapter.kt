package com.android.smartfarm.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.smartfarm.data.entity.NoticeEntity
import com.android.smartfarm.databinding.NoticeItemBinding
import com.android.smartfarm.ui.listener.RecyclerViewItemClickListener
import javax.inject.Inject

class NoticeAdapter @Inject constructor(): RecyclerView.Adapter<NoticeAdapter.ViewHolder>(), RecyclerViewItemClickListener {
    private val items = ArrayList<NoticeEntity>()
    private var listener:RecyclerViewItemClickListener ?= null
    inner class ViewHolder(private val binding: NoticeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item:NoticeEntity){
            binding.noticeItemText.text=item.info
        }
        init {
            binding.root.setOnClickListener{
                onItemClickedListener(binding.noticeItemText.text.toString(),adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NoticeItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items:List<NoticeEntity>){
        this.items.clear()
        this.items.addAll(items)
    }

    fun getItem(pos:Int):NoticeEntity{
        return items[pos]
    }

    override fun onItemClickedListener(name: String, pos: Int) {
        this.listener?.onItemClickedListener(name,pos)
    }

    fun setOnItemClickListener(listener: RecyclerViewItemClickListener){
        this.listener=listener
    }
}