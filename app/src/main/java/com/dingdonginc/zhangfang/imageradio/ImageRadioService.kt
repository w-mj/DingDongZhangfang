package com.dingdonginc.zhangfang.imageradio

import java.lang.ref.WeakReference

object ImageRadioService {
    var view : WeakReference<ImageRadio>? = null
    var vms: WeakReference<List<ImageRadioItemViewModel>>? = null

    fun click(i: Int) {
        vms!!.get()!!.forEach { it.clicked.set(false) }
        vms!!.get()!![i].clicked.set(true)
        view!!.get()!!.selected = i
    }
}