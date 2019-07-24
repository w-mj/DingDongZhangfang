package com.dingdonginc.zhangfang.imageradio

import androidx.databinding.ObservableField

class ImageRadioItemViewModel(icon: Int) {
    val src = ObservableField(0)
    val clicked = ObservableField<Boolean>(true)
    init {
        src.set(icon)
    }

    fun onClick(i: Int) = ImageRadioService.click(i)
}