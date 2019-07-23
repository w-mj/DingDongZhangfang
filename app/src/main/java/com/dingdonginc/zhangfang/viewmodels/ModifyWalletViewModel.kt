package com.dingdonginc.zhangfang.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.services.MessageService
import org.kodein.di.generic.instance

class ModifyWalletViewModel: ViewModel() {
    val input = ObservableField<String>("0")
    val banlance = ObservableField<Int>(0)
    fun submit() {
        val messageService: MessageService by App.getKodein().instance()
        val handler = messageService.getHandler(WalletViewModel::class.java)
        val msg = handler.obtainMessage()
        msg.what = 1
        msg.arg1 = banlance.get()!!
        handler.sendMessage(msg)
    }
}