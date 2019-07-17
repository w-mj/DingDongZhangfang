package com.dingdonginc.zhangfang.models

import com.dingdonginc.zhangfang.BR
import com.dingdonginc.zhangfang.layoutservice.DayAccountAdapter

class DayAccounts {
    val date : String = "14日"
    val weekday : String = "·星期日"
    val inCome : String  = "0"
    val outCome : String = "30"
    var accounts : ArrayList<Account> = ArrayList()
    lateinit var _dayAccountAdapter : DayAccountAdapter
    init {
        var account = Account()
        account.tag = Tag()
        account.tag.name = "支付宝"
        account.tag.icon = 1
        account.amount = -10
        account.comment = "-8.00"
        accounts.add(account)
        accounts.add(account)
        accounts.add(account)
        _dayAccountAdapter = DayAccountAdapter(BR.item, accounts)
    }
}