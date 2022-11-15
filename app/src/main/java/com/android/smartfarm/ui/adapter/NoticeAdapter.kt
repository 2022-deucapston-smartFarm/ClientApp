package com.android.smartfarm.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.smartfarm.data.entity.NoticeEntity
import com.android.smartfarm.databinding.NoticeItemBinding
import javax.inject.Inject

class NoticeAdapter @Inject constructor(): RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {
    private val items = ArrayList<NoticeEntity>()
    inner class ViewHolder(private val binding: NoticeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item:NoticeEntity){
            binding.noticeItemText.text=item.info
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
}