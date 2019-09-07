package com.dingdonginc.zhangfang.lib.bindingadapter.imagebutton

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.lib.command.RelayCommand
import com.dingdonginc.zhangfang.services.MainActivityDialogService
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import org.kodein.di.generic.instance
import java.util.*

class ViewBindingAdapter {
    companion object{
        @JvmStatic
        @BindingAdapter("buttonClickCommand")
        fun buttonClick(view: ImageButton, clickCommand: RelayCommand<Nothing>){
            view.setOnClickListener(ButtonClickListener(clickCommand))
        }

        @JvmStatic
        @BindingAdapter("buttonClickCommand")
        fun buttonStringClick(view: ImageButton, clickCommand: RelayCommand<String>){
            view.setOnClickListener(ButtonStringClickListener(clickCommand))
        }

        @JvmStatic
        @BindingAdapter("buttonClickCommand")
        fun buttonDateClick(view: ImageButton, clickCommand: RelayCommand<Date>){
            view.setOnClickListener(ButtonDateClickListener(clickCommand))
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
}