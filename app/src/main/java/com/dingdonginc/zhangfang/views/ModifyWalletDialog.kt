package com.dingdonginc.zhangfang.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.dingdonginc.zhangfang.BR
import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.databinding.WalletModifyNormalDialogBinding
import com.dingdonginc.zhangfang.models.Wallet
import com.dingdonginc.zhangfang.viewmodels.ModifyWalletViewModel

class ModifyWalletDialog() : DialogFragment() {
    private lateinit var binding: WalletModifyNormalDialogBinding
    private var vm: ModifyWalletViewModel? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.wallet_modify_normal_dialog, container, false)
//        val view = inflater.inflate(R.layout.wallet_modify_normal_dialog, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        vm = ViewModelProviders.of(activity!!).get(ModifyWalletViewModel::class.java)
        binding.setVariable(BR.vm, vm)
        super.onActivityCreated(savedInstanceState)
    }
}