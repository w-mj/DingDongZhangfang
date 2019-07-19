package com.dingdonginc.zhangfang.models

import com.dingdonginc.zhangfang.BR
import com.dingdonginc.zhangfang.layoutservice.DayAccountAdapter

class DayAccounts(var accounts : ArrayList<Account> = ArrayList(),
                  val date : String, val weekday : String,
                  val inCome : String, val outCome : String) {
    var _dayAccountAdapter : DayAccountAdapter
    init {
        _dayAccountAdapter = DayAccountAdapter(BR.item, accounts)
    }
}