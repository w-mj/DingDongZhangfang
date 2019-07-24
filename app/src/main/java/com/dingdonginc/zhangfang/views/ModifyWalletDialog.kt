package com.dingdonginc.zhangfang.views

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableRow
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.dingdonginc.zhangfang.BR
import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.databinding.WalletModifyNormalDialogBinding
import com.dingdonginc.zhangfang.models.Wallet
import com.dingdonginc.zhangfang.viewmodelfactorys.ModifyWalletViewModelFactory
import com.dingdonginc.zhangfang.viewmodels.ModifyWalletViewModel

class ModifyWalletDialog(private val wallet: Wallet) : DialogFragment() {
    private lateinit var binding: WalletModifyNormalDialogBinding
    private var vm: ModifyWalletViewModel? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.wallet_modify_normal_dialog, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        vm = ViewModelProviders.of(this, ModifyWalletViewModelFactory(wallet)).get(ModifyWalletViewModel::class.java)
        binding.setVariable(BR.vm, vm)
        view!!.findViewById<Button>(R.id.wallet_modify_normal_submit).setOnClickListener(OnSubmit())
        view!!.findViewById<Button>(R.id.wallet_modify_normal_cancel).setOnClickListener(OnCancel())
        super.onActivityCreated(savedInstanceState)
    }

    private inner class OnSubmit: View.OnClickListener {
        override fun onClick(p0: View?) {
            this@ModifyWalletDialog.dismiss()
            vm!!.submit()
        }
    }

    private inner class OnCancel: View.OnClickListener {
        override fun onClick(p0: View?) {
            this@ModifyWalletDialog.dismiss()
        }
    }
}