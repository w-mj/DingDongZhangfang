package com.dingdonginc.zhangfang.viewmodels

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel;

class WalletViewModel : ViewModel() {
    val text = ObservableField<String>()

    init {
        text.set("12")
    }

    fun update(view: View) {
        text.set(text.get() + "3")
    }
}
