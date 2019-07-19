package com.dingdonginc.zhangfang.viewmodels

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel;

class AddAccountViewModel : ViewModel() {
    val currentInput = ObservableField<String>()
    val sum = 0

    init {
//        currentInput.set(0)
        currentInput.set("")
    }

    private var kbState = 0


    fun onDigitalKeyClick(view: View) {
        val b: Button = view as Button
        val str = currentInput.get()?: ""
        if (str == "") {
            if (b.text == "+" || b.text == "-")
                return
            if (b.text == ".")
                currentInput.set("0.")
            else
                currentInput.set(b.text.toString())
        } else {
            if (b.text == "+" || b.text == "-") {
                if (str.last() == '+' || str.last() == '-')
                    return
                if (str.last() == '.')
                    currentInput.set(str.substring(0, str.lastIndex) + b.text)
                else
                    currentInput.set(str + b.text)
            } else if (b.text == ".") {
                var i = str.lastIndex
                while (i >= 0) {
                    if (str[i] == '+' || str[i] == '-')
                        break
                    if (str[i] == '.')
                        return
                    i--
                }
                if (i == str.lastIndex)
                    currentInput.set("${str}0.")
                else
                    currentInput.set("$str.")
            } else {
                if (str.length > 3 && str[str.length - 3] == '.')
                    return
                currentInput.set(str + b.text.toString())
            }
        }
    }

    fun onBackspaceClick(view: View) {
        val str = currentInput.get()?:""
        if (str == "")
            return
        currentInput.set(str.substring(0, str.length - 1))
    }
}
