package com.android.smartfarm.ui.adapter

import android.annotation.SuppressLint
import android.widget.ImageView
import androidx.annotation.RawRes
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField

@SuppressLint("ResourceType")
@BindingAdapter("setImageResource")
fun setImageResource(imgView:ImageView,@RawRes imgRes:ObservableField<Int>){
    imgView.setImageResource(imgRes.get()!!)
}