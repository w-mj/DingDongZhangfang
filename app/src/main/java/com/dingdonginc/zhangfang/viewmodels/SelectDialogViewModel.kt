package com.dingdonginc.zhangfang.viewmodels

import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import java.util.*

class SelectDialogViewModel: ViewModel() {
    var methods = BooleanArray(4){true}
    var startdate = ObservableField<String>()
    var enddate = ObservableField<String>()
    var minMoney = ObservableField<String>()
    var maxMoney = ObservableField<String>()

    init {
        minMoney.set("0")
        maxMoney.set("0")
    }

    fun confirm(view: View){
        Log.d(minMoney.get(), maxMoney.get())
        Log.d(methods[0].toString(),methods[1].toString())
        Log.d(startdate.get(),enddate.get())
    }
}