package com.dingdonginc.zhangfang.viewmodels

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
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

class AccountListViewModel : ViewModel(){
    var _contentMainAdapter : ContentMainAdapter ?= null
    var list = ArrayList<DayAccounts>()
    private lateinit var options: ArrayList<Int>
    init {
        val accountService: AccountService by App.getKodein().instance()
        list = Converter.AccListToDayAccList(accountService.getAll() as ArrayList<Account>)
        _contentMainAdapter = ContentMainAdapter(BR.dayAccounts, list)

        options = ArrayList<Int>()
        options.add(0)
        options.add(0)
        options.add(0)
        options.add(0)
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

    fun onitemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long){
        var spinner = p0 as Spinner
        when(spinner.tag){
            "1" -> options[0] = p2
            "2" -> options[1] = p2
            "3" -> options[2] = p2
            "4" -> options[3] = p2
        }
        Log.d("options", options.toString())
    }
}