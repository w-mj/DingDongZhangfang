package com.dingdonginc.zhangfang.viewmodels

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.BR
import com.dingdonginc.zhangfang.layoutservice.ContentMainAdapter
import com.dingdonginc.zhangfang.layoutservice.DayAccountAdapter
import com.dingdonginc.zhangfang.models.*
import com.dingdonginc.zhangfang.services.AccountService
import com.dingdonginc.zhangfang.services.MainActivityDialogService
import com.dingdonginc.zhangfang.services.MessageService
import com.dingdonginc.zhangfang.services.converter.Converter
import com.dingdonginc.zhangfang.views.SelectDialog
import org.kodein.di.generic.instance
import java.util.*
import kotlin.collections.ArrayList

class AccountListViewModel : ViewModel(), Handler.Callback{
    var _contentMainAdapter : ContentMainAdapter ?= null
    var list = ArrayList<DayAccounts>()
    private lateinit var options: ArrayList<Int>
    private val accountService: AccountService by App.getKodein().instance()
    init {
        val messageService: MessageService by App.getKodein().instance()
        messageService.register(this)
        list = Converter.AccListToDayAccList(this, accountService.getAll() as ArrayList<Account>)
        _contentMainAdapter = ContentMainAdapter(BR.dayAccounts, list)

        options = ArrayList<Int>()
        options.add(0)
        options.add(0)
        options.add(0)
        options.add(0)
    }

    fun refresh(view: View){
        val temp = Converter.AccListToDayAccList(this, accountService.getAll() as ArrayList<Account>)
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

    override fun handleMessage(msg: Message): Boolean {
        val bundle: Bundle = msg.getData()
        val sDate = Date(bundle.getInt("syear")-1900, bundle.getInt("smonth"), bundle.getInt("sday"))
        val eDate = Date(bundle.getInt("eyear")-1900, bundle.getInt("emonth"), bundle.getInt("eday"))
        val AllAccounts = accountService.getAll() as ArrayList<Account>
        val tempAccounts  = ArrayList<Account>()
        var income = 0F
        var outcome = 0F
        val wallet = Wallet("微信",WalletType.Real,"", false, 1)
        for(acc in AllAccounts){
            acc.wallet = wallet
            Log.d(acc.time.toString(), sDate.toString())
            Log.d(acc.time.toString(), eDate.toString())
            Log.d("compare", (acc.time >= sDate).toString())
            Log.d("compare", (acc.time <= eDate).toString())
            if(acc.time >= sDate && acc.time <= eDate) {
                Log.d("1st",acc.amount.toString())
                if (Math.abs(acc.amount) >= 100 * bundle.getFloat("min")
                    && Math.abs(acc.amount) <= 100 * bundle.getFloat("max")) {
                    Log.d("2st",acc.wallet.name)
                    if (bundle.getStringArrayList("methods")?.contains(acc.wallet.name)!!) {
                        tempAccounts.add(acc)
                        if (acc.amount >= 0)
                            income += acc.amount/100F
                        else
                            outcome += -acc.amount/100F
                    }
                }
            }
        }
        list.clear()
        list.add(DayAccounts(this, tempAccounts, "筛选结果", "", income, outcome))
        _contentMainAdapter?.notifyDataSetChanged()
        return true
    }

    fun info(view: View){
        val linearLayout: LinearLayout = view as LinearLayout
        val id = linearLayout.tag
        val tempAccounts = accountService.getAll()
        val account = tempAccounts.find { it.id == id }
        val mainActivityDialogService: MainActivityDialogService by App.getKodein().instance()
        mainActivityDialogService.showInfoDialog(account!!)
    }
}