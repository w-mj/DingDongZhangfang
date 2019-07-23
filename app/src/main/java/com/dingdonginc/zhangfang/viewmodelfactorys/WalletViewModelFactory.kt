package com.dingdonginc.zhangfang.viewmodelfactorys

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dingdonginc.zhangfang.viewmodels.WalletViewModel

class WalletViewModelFactory(private val showDialog: ()-> Unit) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WalletViewModel() as T
    }
}