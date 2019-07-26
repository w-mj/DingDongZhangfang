package com.dingdonginc.zhangfang.lib.bindingadapter.imageview

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.dingdonginc.zhangfang.lib.command.RelayCommand

class ViewBindingAdapter {
    companion object{
        @JvmStatic
        @BindingAdapter("uri")
        fun setUri(view: ImageView, resId: Int) {
            view.setImageResource(resId)
        }

        @JvmStatic
        @BindingAdapter("imageClick")
        fun imageOnClick(view: ImageView, imageClickCommand: RelayCommand<Nothing>){
            view.setOnClickListener(ImageClickListener(imageClickCommand))
        }
    }

    private class ImageClickListener(val onClick: RelayCommand<Nothing>): View.OnClickListener{
        override fun onClick(p0: View?) {
            onClick.execute()
        }
    }
}