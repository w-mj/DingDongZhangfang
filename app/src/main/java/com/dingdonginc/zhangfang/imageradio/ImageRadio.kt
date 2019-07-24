package com.dingdonginc.zhangfang.imageradio

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.ListView
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

class ImageRadio: ListView {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle)

    override fun setAdapter(ad: ListAdapter?) {
        super.setAdapter(ad)
        children.forEachIndexed { index, view -> view.setOnClickListener{onClick(index)} }
        Log.i("ImageRadio", children.count().toString())
    }


    private val onClick : (Int)->Unit = {
        for (i in 0 until count) {
            val vm = adapter.getItem(i) as ImageRadioItemViewModel
            vm.clicked.set(false)
        }
        val vm = adapter.getItem(it) as ImageRadioItemViewModel
        vm.clicked.set(false)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:background")
        fun setBackground(view: ImageView, boundary: Boolean) {
            if (boundary)
                view.setBackgroundColor(0xFFFFC107.toInt())
            else
                view.setBackgroundColor(0x00FFC107)
        }
    }
}