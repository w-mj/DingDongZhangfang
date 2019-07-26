package com.dingdonginc.zhangfang.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.dingdonginc.zhangfang.BR
import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.databinding.WalletModifyNeuDialogBinding
import com.dingdonginc.zhangfang.viewmodels.ModifyWalletNEUViewModel

class ModifyWalletNEUDialog: DialogFragment() {
    private lateinit var binding: WalletModifyNeuDialogBinding
    private var vm: ModifyWalletNEUViewModel? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.wallet_modify_neu_dialog, container, false)
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        vm = ViewModelProviders.of(this).get(ModifyWalletNEUViewModel::class.java)
        binding.setVariable(BR.vm, vm)
        super.onActivityCreated(savedInstanceState)
    }
}