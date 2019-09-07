package com.dingdonginc.zhangfang.lib.bindingadapter.recyclerview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewBindingAdapter {
    companion object{
        @JvmStatic
        @BindingAdapter(value = ["adapter"])
        fun <T : RecyclerView.ViewHolder>setRecyclerViewAdapter(
            recyclerView: RecyclerView,
            adapter: RecyclerView.Adapter<T>)
        {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.adapter = adapter
        }
    }
}