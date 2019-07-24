package com.dingdonginc.zhangfang.services

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.dingdonginc.zhangfang.models.Wallet
import com.dingdonginc.zhangfang.views.ModifyWalletDialog

class MainActivityDialogService {
    private var fm: FragmentManager?=null

    fun setFm(a: FragmentManager) {
        fm = a
    }

    fun showNormalDialog(wallet: Wallet) {
        val d = ModifyWalletDialog(wallet)
        d.show(fm!!, "Modify")
    }

    fun showDialog(dialog: DialogFragment, tag: String) {
        dialog.show(fm!!, tag)
    }
}