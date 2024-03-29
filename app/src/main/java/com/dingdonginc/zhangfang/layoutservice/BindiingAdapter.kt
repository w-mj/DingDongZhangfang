package com.dingdonginc.zhangfang.layoutservice

import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.ProgressBar
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
        fun setAdapter(listView: ListView, adapter: BaseAdapter) {
            Log.i("setAdapter", "List Adapter")
            listView.adapter = adapter
        }

        @JvmStatic
        @BindingAdapter("android:src")
        fun setSrc(view: ImageView, resId: Int) {
            view.setImageResource(resId)
        }

        @JvmStatic
        @BindingAdapter("android:src")
        fun setSrc(view: ImageView, bitmap: Bitmap?) {
            if (bitmap != null)
                view.setImageBitmap(bitmap)
        }

        @JvmStatic
        @BindingAdapter(value = ["android:visibility"])
        fun setVisibility(view: View, vis: Boolean) {
            if (vis)
                view.visibility = View.VISIBLE
            else
                view.visibility = View.GONE
        }


        @JvmStatic
        @BindingAdapter("loadingcaptcha")
        fun setLoadingCaptcha(v: ProgressBar, loading: Boolean) {
            v.visibility = if (loading) View.VISIBLE else View.GONE
        }

        @JvmStatic
        @BindingAdapter("loadingcaptcha")
        fun setLoadingCaptcha(v: ImageView, loading: Boolean) {
            v.visibility = if (loading) View.GONE else View.VISIBLE
        }
    }
}