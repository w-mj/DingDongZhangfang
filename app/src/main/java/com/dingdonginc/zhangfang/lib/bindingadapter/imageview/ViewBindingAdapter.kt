package com.dingdonginc.zhangfang.lib.bindingadapter.imageview

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.dingdonginc.zhangfang.lib.command.RelayCommand

class ViewBindingAdapter {
    companion object{
        @JvmStatic
        @BindingAdapter("uri")
        fun setSrc(view: ImageView, resId: Int) {
            view.setImageResource(resId)
        }

        @JvmStatic
        @BindingAdapter("imageClick")
        fun imageOnClick(view: ImageView, onSuccessCommand: RelayCommand<Int>){
            view.setOnClickListener(OnClickListener(onSuccessCommand))
        }
    }

    private class OnClickListener(val onClick: RelayCommand<Int>): View.OnClickListener{
        override fun onClick(p0: View?) {
            onClick.execute()
        }
    }
}