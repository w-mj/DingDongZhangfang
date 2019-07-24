package com.dingdonginc.zhangfang.viewmodelfactorys

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dingdonginc.zhangfang.models.Wallet
import com.dingdonginc.zhangfang.viewmodels.ModifyWalletViewModel

class ModifyWalletViewModelFactory(private val w: Wallet, private val autoFetch: Boolean): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ModifyWalletViewModel(w, autoFetch) as T
    }
}