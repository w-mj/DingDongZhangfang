package com.dingdonginc.zhangfang.layoutservice

import android.util.Log
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dingdonginc.zhangfang.models.Tag

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

        @JvmStatic
        @BindingAdapter(value = ["adapter"])
        fun setAdapter(listView: ListView, adapter: BaseAdapter) {
            Log.i("setAdapter", "List Adapter")
            listView.adapter = adapter
        }

        @BindingAdapter("android:src")
        fun setSrc(view: ImageView, resId: Int) {
            view.setImageResource(resId)
        }
    }
}