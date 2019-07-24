package com.dingdonginc.zhangfang.viewmodels

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.services.MessageService
import org.kodein.di.generic.instance
import java.util.*
import kotlin.concurrent.timerTask

class SelectDialogViewModel: ViewModel() {
    var methods = BooleanArray(4){true}
    var startdate = ObservableField<String>()
    var enddate = ObservableField<String>()
    var minMoney = ObservableField<String>()
    var maxMoney = ObservableField<String>()
    private var syear = 0
    private var smonth = 0
    private var sday = 0
    private var eyear = 0
    private var emonth = 0
    private var eday = 0

    init {
        minMoney.set("0")
        maxMoney.set("0")
    }

    fun confirm(view: View){
        Log.d(minMoney.get(), maxMoney.get())
        Log.d(methods[0].toString(),methods[1].toString())
        Log.d(startdate.get(),enddate.get())
    }

    fun setStart(year: Int, month: Int, day: Int){
        syear = year
        smonth = month
        sday = day
        startdate.set(String.format("%4d-%02d-%02d", year, month+1, day))
    }

    fun setEnd(year: Int, month: Int, day: Int){
        eyear = year
        emonth = month
        eday = day
        enddate.set(String.format("%4d-%02d-%02d", year, month+1, day))
    }

    fun submit(view: View) {
        val messageService: MessageService by App.getKodein().instance()
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
    }
}