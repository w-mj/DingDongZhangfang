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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timerTask

class SelectDialogViewModel: ViewModel() {
    /* * * binding data * * */
    var methods = BooleanArray(4){true}
    var startdate = ObservableField<String>()
    var enddate = ObservableField<String>()
    var minMoney = ObservableField<String>("0")
    var maxMoney = ObservableField<String>("0")

    /* * * private variables * * */
    private var startDay: Date
    private var endDay: Date
    private val parser = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)

    /* * * private services * * */
    private val messageService: MessageService by App.getKodein().instance()

    init {
        //initial date is error
        startDay = Date()
        endDay = Date()
        startdate.set(parser.format(startDay))
        enddate.set(parser.format(endDay))
    }
    /**
     * @summary Set startDate when you use accounts filter function
     * @param date is from Date Picker
     */
    val setStartDay = RelayCommand<Date>(Action1<Date>{ date->
        startDay = date
        startdate.set(String.format("%4d-%02d-%02d", date.year, date.month + 1, date.date))
    })

    /**
     * @summary Set endDate when you use accounts filter function
     * @param date is from Date Picker
     */
    val setEndDay = RelayCommand<Date>(Action1<Date> { date->
        endDay = date
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
        bundle.putInt("syear", startDay.year)
        bundle.putInt("smonth", startDay.month)
        bundle.putInt("sday", startDay.date)
        bundle.putInt("eyear", endDay.year)
        bundle.putInt("emonth", endDay.month)
        bundle.putInt("eday", endDay.date)
        bundle.putFloat("min", minMoney.get()!!.toFloat())
        bundle.putFloat("max", maxMoney.get()!!.toFloat())
        msg.setData(bundle)
        handler.sendMessage(msg)
    })
}