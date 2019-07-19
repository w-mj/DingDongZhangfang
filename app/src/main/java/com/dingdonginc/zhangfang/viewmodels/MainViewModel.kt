package com.dingdonginc.zhangfang.viewmodels

import android.view.View
import androidx.lifecycle.ViewModel
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.BR
import com.dingdonginc.zhangfang.layoutservice.ContentMainAdapter
import com.dingdonginc.zhangfang.layoutservice.DayAccountAdapter
import com.dingdonginc.zhangfang.models.Account
import com.dingdonginc.zhangfang.models.Check
import com.dingdonginc.zhangfang.models.DayAccounts
import com.dingdonginc.zhangfang.models.Tag
import com.dingdonginc.zhangfang.services.AccountService
import com.dingdonginc.zhangfang.services.converter.Converter
import org.kodein.di.generic.instance

class MainViewModel : ViewModel(){
    var _contentMainAdapter : ContentMainAdapter ?= null
    var list = ArrayList<DayAccounts>()
    init {
        val accountService: AccountService by App.getKodein().instance()
        list = Converter.AccListToDayAccList(accountService.getAll() as ArrayList<Account>)
        _contentMainAdapter = ContentMainAdapter(BR.dayAccounts, list)
    }

    fun refresh(view: View){
        val accountService: AccountService by App.getKodein().instance()
        val temp = Converter.AccListToDayAccList(accountService.getAll() as ArrayList<Account>)
        list.clear()
        list.addAll(temp)
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