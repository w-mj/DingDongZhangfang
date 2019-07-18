package com.dingdonginc.zhangfang.viewmodels

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel

class DigitalKeyboardViewModel: ViewModel() {
    val currentInput = ObservableInt()
    val sum = 0

    init {
        currentInput.set(0)
    }

    fun onDigitalKeyClick(view: View) {
        val b: Button = view as Button
        Log.i("digit click", b.text.toString())
        currentInput.set(currentInput.get() + 1)
    }

}