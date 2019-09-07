package com.dingdonginc.zhangfang.lib.bindingadapter.button

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.databinding.BindingAdapter
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.lib.command.RelayCommand
import com.dingdonginc.zhangfang.services.MainActivityDialogService
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.digital_keyboard.view.*
import org.kodein.di.generic.instance
import java.util.*

class ViewBindingAdapter {
    companion object{
        @JvmStatic
        @BindingAdapter("buttonClickCommand")
        fun buttonClick(view: Button, clickCommand: RelayCommand<Nothing>){
            view.setOnClickListener(ButtonClickListener(clickCommand))
        }

        @JvmStatic
        @BindingAdapter("buttonClickCommand")
        fun buttonStringClick(view: Button, clickCommand: RelayCommand<String>){
            view.setOnClickListener(ButtonStringClickListener(clickCommand))
        }

        @JvmStatic
        @BindingAdapter("buttonClickCommand")
        fun buttonDateClick(view: Button, clickCommand: RelayCommand<Date>){
            view.setOnClickListener(ButtonDateClickListener(clickCommand))
        }

        @JvmStatic
        @BindingAdapter(value = ["mode", "callBackCommand"], requireAll = false)
        fun dateButtonClick(view: Button, mode: String, callBack: RelayCommand<String>) {
            view.setOnClickListener(ButtonDateKeyClickListener(mode, callBack))
        }
    }

    private class ButtonClickListener(val onClick: RelayCommand<Nothing>): View.OnClickListener{
        override fun onClick(p0: View?) {
            onClick.execute()
        }
    }

    private class ButtonStringClickListener(val onClick: RelayCommand<String>): View.OnClickListener{
        override fun onClick(p0: View?) {
            val button = p0 as Button
            onClick.execute(button.text.toString())
        }
    }

    private class ButtonDateClickListener(val onClick: RelayCommand<Date>): View.OnClickListener{
        override fun onClick(p0: View?) {
            val mainActivityDialogService: MainActivityDialogService by App.getKodein().instance()
            mainActivityDialogService.showDatePickerDialog(onClick)
        }
    }

    private class ButtonDateKeyClickListener(val mode: String, val callBack: RelayCommand<String>): View.OnClickListener{
        override fun onClick(p0: View?) {
            val mainActivityDialogService: MainActivityDialogService by App.getKodein().instance()
            if(mode == "Calendar"){
                mainActivityDialogService.showCalendarDialog(callBack)
            }
            else if(mode == "Time"){
                mainActivityDialogService.showTimeDialog(callBack)
            }
            else if(mode == "CalendarTime"){
                mainActivityDialogService.showCalendarTimeDialog(callBack)
            }

        }
    }
}