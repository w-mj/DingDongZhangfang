package com.dingdonginc.zhangfang.services

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.dingdonginc.zhangfang.lib.bindingadapter.button.ViewBindingAdapter
import com.dingdonginc.zhangfang.lib.command.RelayCommand
import com.dingdonginc.zhangfang.models.Account
import com.dingdonginc.zhangfang.models.Wallet
import com.dingdonginc.zhangfang.views.InfoDialog
import com.dingdonginc.zhangfang.views.ModifyWalletDialog
import com.dingdonginc.zhangfang.views.SelectDialog
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
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

    fun showDialog(dialog: DialogFragment, tag: String) {
        dialog.show(fm!!, tag)
    }

    /* * * show account's detailed infomation * * */
    fun showInfoDialog(acc: Account){
        val dialog = InfoDialog(acc)
        dialog.show(fm!!, "InfoDialog")
    }

    /* * * show filter dialog * * */
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

    /* * * only show calendar * * */
    fun showCalendarDialog(onClick: RelayCommand<String>){
        val datePicker = DatePickerDialog.newInstance(OnCalendarCallBack(onClick))
        datePicker.show(fm!!, "CalendarDialog")
    }
    // ↑监听楼上
    private inner class OnCalendarCallBack(val onClick: RelayCommand<String>): DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
            onClick.execute(String.format("%4d-%02d-%02d", year, monthOfYear, dayOfMonth))
        }
    }

    /* * * only show time * * */
    fun showTimeDialog(onClick: RelayCommand<String>){
        val timePicker = TimePickerDialog.newInstance(OnTimeCallBack(onClick), true)
        timePicker.setTimeInterval(1, 5)
        timePicker.enableSeconds(false)
        timePicker.show(fm!!, "TimeDialog")
    }
    // ↑监听楼上
    private inner class OnTimeCallBack(val onClick: RelayCommand<String>): TimePickerDialog.OnTimeSetListener {
        override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
            onClick.execute(String.format("%02d:%02d", hourOfDay, minute))
        }
    }

    /* * * show calendar and time * * */
    fun showCalendarTimeDialog(onClick: RelayCommand<String>){
        val datePicker = DatePickerDialog.newInstance(OnCalendarTimeCallBack(onClick))
        datePicker.show(fm!!, "CalendarTimeDialog")
    }
    // ↑监听楼上
    private inner class OnCalendarTimeCallBack(val onClick: RelayCommand<String>): DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
            showTime2Dialog(onClick, year, monthOfYear, dayOfMonth)
        }
    }

    fun showTime2Dialog(onClick: RelayCommand<String>, year: Int, month: Int, day: Int){
        val timePicker = TimePickerDialog.newInstance(OnTime2CallBack(onClick, year, month, day), true)
        timePicker.setTimeInterval(1, 5)
        timePicker.enableSeconds(false)
        timePicker.show(fm!!, "TimePickerKeyDialog")
    }

    private inner class OnTime2CallBack(
        val onClick: RelayCommand<String>,
        val year: Int, val month: Int, val day: Int
    ): TimePickerDialog.OnTimeSetListener {
        override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
            onClick.execute(String.format("%4d-%02d-%02d\n%02d:%02d", year, month, day, hourOfDay, minute))
        }
    }
}