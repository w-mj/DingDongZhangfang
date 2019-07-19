package com.dingdonginc.zhangfang.layoutservice

import android.widget.BaseAdapter
import android.widget.ListView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/*
   为recyclerview 自定义属性 app:adapter
   辅助绑定adapter
*/
class BindiingAdapter {
    companion object{
        @JvmStatic
        @BindingAdapter(value = ["adapter"])
        fun <T : RecyclerView.ViewHolder>setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<T>) {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.adapter = adapter
        }
    }
}