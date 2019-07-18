package com.dingdonginc.zhangfang.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import com.dingdonginc.zhangfang.BR
import com.dingdonginc.zhangfang.layoutservice.ContentMainAdapter
import com.dingdonginc.zhangfang.layoutservice.DayAccountAdapter
import com.dingdonginc.zhangfang.models.Account
import com.dingdonginc.zhangfang.models.Check
import com.dingdonginc.zhangfang.models.DayAccounts
import com.dingdonginc.zhangfang.models.Tag

class MainViewModel : ViewModel(){
    var _contentMainAdapter : ContentMainAdapter ?= null
    val list = ArrayList<DayAccounts>()
    init {
        list.add(DayAccounts())
        list.add(DayAccounts())
        list.add(DayAccounts())
        _contentMainAdapter = ContentMainAdapter(BR.dayAccounts, list)
    }

    fun refresh(view: View){
        list.add(DayAccounts())
        _contentMainAdapter?.notifyDataSetChanged()
    }

    fun itemrefresh(view: View){
        var account = Account()
        account.tag = Tag()
        account.tag.name = "支付宝"
        account.tag.icon = 1
        account.amount = -10
        account.comment = "-8.00"
        list[0].accounts.add(account)
        list[1].accounts.add(account)
        //list[0]._dayAccountAdapter.notifyDataSetChanged()
        _contentMainAdapter?.notifyDataSetChanged()
    }
}