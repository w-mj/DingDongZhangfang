package com.dingdonginc.zhangfang.services

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.dingdonginc.zhangfang.lib.command.RelayCommand
import com.dingdonginc.zhangfang.models.Account
import com.dingdonginc.zhangfang.models.Wallet
import com.dingdonginc.zhangfang.views.InfoDialog
import com.dingdonginc.zhangfang.views.ModifyWalletDialog
import com.dingdonginc.zhangfang.views.ModifyWalletNEUDialog
import com.dingdonginc.zhangfang.views.SelectDialog
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*

class MainActivityDialogService {
    private var fm: FragmentManager?=null

    fun setFm(a: FragmentManager) {
        fm = a
    }

    fun showNormalDialog(wallet: Wallet) {
        val d = ModifyWalletDialog(wallet)
        d.show(fm!!, "Modify")
    }

    fun showNEUDialog() {
        val d = ModifyWalletNEUDialog()
        d.show(fm!!, "Modify NEU")
    }

    fun showDialog(dialog: DialogFragment, tag: String) {
        dialog.show(fm!!, tag)
    }

    fun showInfoDialog(acc: Account){
        val dialog = InfoDialog(acc)
        dialog.show(fm!!, "InfoDialog")
    }

    fun showFilterDialog(){
        val dialog = SelectDialog()
        dialog.show(fm!!, "FilterDialog")
    }

    fun showDatePickerDialog(onClick: RelayCommand<Date>){
        val datePicker = DatePickerDialog.newInstance(OnDatePickerCallBack(onClick))
        datePicker.show(fm!!, "DatePickerDialog")
    }

    private inner class OnDatePickerCallBack(val onClick: RelayCommand<Date>): DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
            onClick.execute(Date(year, monthOfYear, dayOfMonth))
        }
    }
}