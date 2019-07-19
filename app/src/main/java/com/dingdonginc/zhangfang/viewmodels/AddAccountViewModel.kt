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
            // 输入序列为空
            if (b.text == "+" || b.text == "-")  // 忽略第一个+-
                return
            if (b.text == ".")  // 第一个.前面加0
                currentInput.set("0.")
            else
                currentInput.set(b.text.toString())  // 数字键
        } else {
            if (b.text == "+" || b.text == "-") {
                if (str.last() == '+' || str.last() == '-')  // 忽略连续的符号
                    return
                if (str.last() == '.')  // .后面直接接符号，去掉.
                    currentInput.set(str.substring(0, str.lastIndex) + b.text)
                else
                    currentInput.set(str + b.text)  // 加入符号
            } else if (b.text == ".") {
                var i = str.lastIndex
                while (i >= 0) {
                    if (str[i] == '+' || str[i] == '-')
                        break
                    if (str[i] == '.')
                        return  // 同一个数字里面不能有两个点
                    i--
                }
                if (i == str.lastIndex)  // 点之前是符号，加个0
                    currentInput.set("${str}0.")
                else
                    currentInput.set("$str.")
            } else {
                if (str.length > 3 && str[str.length - 3] == '.')  // 小数位数不能超过3
                    return
                currentInput.set(str + b.text.toString())  // 加入数字
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
