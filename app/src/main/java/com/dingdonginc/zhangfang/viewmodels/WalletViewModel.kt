package com.dingdonginc.zhangfang.viewmodels

import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.layoutservice.WalletListAdapter
import com.dingdonginc.zhangfang.models.Wallet
import com.dingdonginc.zhangfang.services.MainActivityDialogService
import com.dingdonginc.zhangfang.services.MessageService
import com.dingdonginc.zhangfang.services.WalletService
import com.dingdonginc.zhangfang.views.ModifyWalletDialog
import org.kodein.di.generic.instance



class WalletViewModel : ViewModel(), Handler.Callback {

    var list = ObservableArrayList<Wallet>()
    var adapter = WalletListAdapter(list, this)


//    private val realAdapter: ObservableField<WalletListAdapter>
//    private val virtualAdapter: ObservableField<WalletListAdapter>
    init {
        val messageService: MessageService by App.getKodein().instance()
        messageService.register(this)
        switch(0)
    }

    fun onModifyWallet(wallet: Wallet) {
        Log.i("onModifyWallet", wallet.name)
        val mainActivityDialogService: MainActivityDialogService by App.getKodein().instance()
        if (wallet.predefined && wallet.name == "东北大学校园卡") {
            mainActivityDialogService.showNEUDialog()
        } else {
            mainActivityDialogService.showNormalDialog(wallet)
        }
    }

    fun switch(i: Int) {
        val walletService: WalletService by App.getKodein().instance()
        list.clear()
        when(i) {
            0-> walletService.getReal().forEach { list.add(it)}
            1-> walletService.getVirtual().forEach { list.add(it)}
            else -> throw Exception("WalletViewModel: i must be 1 or 0")
        }
        adapter.notifyDataSetChanged()
    }


    override fun handleMessage(msg: Message): Boolean {
        val w: Wallet = msg.obj as Wallet
        Log.i("Msg", "update billing ${msg.what}: ${w.balance}")
        val walletService: WalletService by App.getKodein().instance()
        walletService.update(w)
        adapter.notifyDataSetChanged()
        return true
    }
}
