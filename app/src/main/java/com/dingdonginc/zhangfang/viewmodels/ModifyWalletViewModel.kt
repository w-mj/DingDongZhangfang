package com.dingdonginc.zhangfang.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.models.Wallet
import com.dingdonginc.zhangfang.services.MessageService
import org.kodein.di.generic.instance

class ModifyWalletViewModel(val wallet: Wallet, val autoFetch: Boolean): ViewModel() {
    init {
        Log.i("modify wallet viewmodel", wallet.name)
    }
    val input = ObservableField<String>(wallet.displayRMB())
    fun submit() {
        val messageService: MessageService by App.getKodein().instance()
        val handler = messageService.getHandler(WalletViewModel::class.java)
        val msg = handler.obtainMessage()
        wallet.balance = (input.get()!!.toFloat() * 100).toInt()
        msg.what = 1
        msg.obj = wallet
        handler.sendMessage(msg)
    }
}