package com.dingdonginc.zhangfang.viewmodels

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.lib.command.RelayCommand
import com.dingdonginc.zhangfang.services.MessageService
import org.kodein.di.generic.instance
import rx.functions.Action0
import rx.functions.Action1
import java.util.*
import kotlin.concurrent.timerTask

class SelectDialogViewModel: ViewModel() {
    var methods = BooleanArray(4){true}
    var startdate = ObservableField<String>()
    var enddate = ObservableField<String>()
    var minMoney = ObservableField<String>("0")
    var maxMoney = ObservableField<String>("0")
    //There is a bug
    private var syear = 0
    private var smonth = 0
    private var sday = 0
    private var eyear = 0
    private var emonth = 0
    private var eday = 0

    private val messageService: MessageService by App.getKodein().instance()

    init {
    }

    /**
     * @summary Set startDate when you use accounts filter function
     * @param date is from Date Picker
     */
    val setStartDay = RelayCommand<Date>(Action1<Date>{ date->
        startdate.set(String.format("%4d-%02d-%02d", date.year, date.month + 1, date.date))
    })

    /**
     * @summary Set endDate when you use accounts filter function
     * @param date is from Date Picker
     */
    val setEndDay = RelayCommand<Date>(Action1<Date> { date->
        enddate.set(String.format("%4d-%02d-%02d", date.year, date.month + 1, date.date))
    })

    /**
     * @summary Obtain filter infomation and send to AccountListViewModel
     * @param no param
     */
    val submit = RelayCommand<Nothing>(Action0 {
        val handler = messageService.getHandler(AccountListViewModel::class.java)
        val msg = handler.obtainMessage()
        val bundle = Bundle()
        var rmethods = ArrayList<String>()
        for (index in methods.indices)
            if(methods[index] == true)
                rmethods.add(when(index){
                    0 -> "支付宝"
                    1 -> "微信"
                    2 -> "银行卡"
                    3 -> "现金"
                    else -> ""
                })
        bundle.putStringArrayList("methods",rmethods)
        bundle.putInt("syear", syear)
        bundle.putInt("smonth", smonth)
        bundle.putInt("sday", sday)
        bundle.putInt("eyear", eyear)
        bundle.putInt("emonth", emonth)
        bundle.putInt("eday", eday)
        bundle.putFloat("min", minMoney.get()!!.toFloat())
        bundle.putFloat("max", maxMoney.get()!!.toFloat())
        msg.setData(bundle)
        handler.sendMessage(msg)
    })
}