package com.dingdonginc.zhangfang.viewmodels

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.lifecycle.ViewModel
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.BR
import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.layoutservice.ContentMainAdapter
import com.dingdonginc.zhangfang.lib.command.RelayCommand

import com.dingdonginc.zhangfang.models.*
import com.dingdonginc.zhangfang.services.AccountService
import com.dingdonginc.zhangfang.services.ActivityService
import com.dingdonginc.zhangfang.services.MainActivityDialogService
import com.dingdonginc.zhangfang.services.MessageService
import com.dingdonginc.zhangfang.services.converter.Converter

import com.dingdonginc.zhangfang.views.AddAccountActivity
import com.dingdonginc.zhangfang.views.SelectDialog
import com.j256.ormlite.stmt.query.Not
import org.kodein.di.generic.instance
import rx.functions.Action0
import java.util.*
import kotlin.collections.ArrayList

class AccountListViewModel : ViewModel(), Handler.Callback{
    /* * * binding data * * */
    val _contentMainAdapter : ContentMainAdapter

    /* * * private variables * * */
    private var list = ArrayList<DayAccounts>()

    /* * * private services * * */
    private val accountService: AccountService by App.getKodein().instance()
    private val mainActivityDialogService: MainActivityDialogService by App.getKodein().instance()
    private val messageService: MessageService by App.getKodein().instance()

    init {
        messageService.register(this)
        list = Converter.AccListToDayAccList(this, accountService.getAll() as ArrayList<Account>)
        _contentMainAdapter = ContentMainAdapter(BR.dayAccounts, list)
    }

    /* * * binding commands * * */
    /**
     * @summary Refresh the AccountListView(page), the newest data is from the database
     * @param no param
     */
    val refresh = RelayCommand<Nothing>(Action0 {
        val temp = Converter.AccListToDayAccList(this, accountService.getAll() as ArrayList<Account>)
        list.clear()
        list.addAll(temp)
        _contentMainAdapter?.notifyDataSetChanged()
    })

    /**
     * @summary Popup filterDialog
     * @param no param
     */
    val filterDialog = RelayCommand<Nothing>(Action0 {
        mainActivityDialogService.showFilterDialog()
    })

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

    fun onAddAccount() {
        val activityService: ActivityService by App.getKodein().instance()
        activityService.switchActivity(AddAccountActivity::class.java)
    }

    fun info(view: View){
        val linearLayout: LinearLayout = view as LinearLayout
        val id = linearLayout.tag
        val tempAccounts = accountService.getAll()
        val account = tempAccounts.find { it.id == id }
        mainActivityDialogService.showInfoDialog(account!!)
    }
}