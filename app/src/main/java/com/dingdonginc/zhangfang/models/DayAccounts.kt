package com.dingdonginc.zhangfang.models

import com.dingdonginc.zhangfang.BR
import com.dingdonginc.zhangfang.layoutservice.DayAccountAdapter

class DayAccounts {
    val date : String = "14日·星期日"
    val inCome : String  = "0"
    val outCome : String = "30"
    var accounts : ArrayList<Account> = ArrayList()
    lateinit var _dayAccountAdapter : DayAccountAdapter
    init {
        var account = Account()
        account.method = Method()
        account.method.name = "支付宝"
        accounts.add(account)
        accounts.add(account)
        accounts.add(account)
        _dayAccountAdapter = DayAccountAdapter(BR.item, accounts)
    }
}