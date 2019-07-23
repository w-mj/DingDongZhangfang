package com.dingdonginc.zhangfang.viewmodels

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.layoutservice.WalletListAdapter
import com.dingdonginc.zhangfang.models.Wallet
import com.dingdonginc.zhangfang.services.WalletService
import org.kodein.di.generic.instance

class WalletViewModel : ViewModel() {
    var list = ObservableArrayList<Wallet>()
    var adapter = WalletListAdapter(list, this)
//    private val realAdapter: ObservableField<WalletListAdapter>
//    private val virtualAdapter: ObservableField<WalletListAdapter>
    init {
        switch(0)
    }

    fun onModifyWallet(wallet: Wallet) {
        Log.i("onModifyWallet", wallet.name)
    }

    fun switch(i: Int) {
//        Log.i("WalletViewModel" , "switch $i")
        val walletService: WalletService by App.getKodein().instance()
        list.clear()
        when(i) {
            0-> walletService.getReal().forEach { list.add(it)}
            1-> walletService.getVirtual().forEach { list.add(it)}
            else -> throw Exception("WalletViewModel: i must be 1 or 0")
        }
        adapter.notifyDataSetChanged()
        // Log.i("WalletViewMode", list.joinToString { it.name })
    }

}
