package com.dingdonginc.zhangfang.lib.bindingadapter.linearlayout

import android.view.View
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.dingdonginc.zhangfang.lib.command.RelayCommand

class ViewBindingAdapter {
    companion object{
        @JvmStatic
        @BindingAdapter("linearClickCommand")
        fun linearClick(view: LinearLayout, clickCommand: RelayCommand<Int>){
            view.setOnClickListener(ButtonClickListener(clickCommand))
        }
    }

    private class ButtonClickListener(val onClick: RelayCommand<Int>): View.OnClickListener{
        override fun onClick(p0: View?) {
            val linearLayout = p0 as LinearLayout
            onClick.execute(linearLayout.tag as Int)
        }
    }
}