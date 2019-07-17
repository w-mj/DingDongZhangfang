package com.dingdonginc.zhangfang.viewmodels

import androidx.lifecycle.ViewModel
import com.dingdonginc.zhangfang.BR
import com.dingdonginc.zhangfang.layoutservice.ContentMainAdapter
import com.dingdonginc.zhangfang.layoutservice.DayAccountAdapter
import com.dingdonginc.zhangfang.models.Check
import com.dingdonginc.zhangfang.models.DayAccounts

class MainViewModel : ViewModel(){
    var _contentMainAdapter : ContentMainAdapter ?= null
    init {
        val list = ArrayList<DayAccounts>()
        list.add(DayAccounts())
        list.add(DayAccounts())
        list.add(DayAccounts())
        _contentMainAdapter = ContentMainAdapter(BR.dayAccounts, list)
    }
}