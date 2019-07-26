package com.dingdonginc.zhangfang.views

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.viewmodels.AccountListViewModel
import com.dingdonginc.zhangfang.viewmodels.SelectDialogViewModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.day_accounts.*


class SelectDialog: DialogFragment() {
    private lateinit var datetimeButton: Button
    private lateinit var datetimeButton1: Button
    private lateinit var viewModel: SelectDialogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MyMiddleDialogStyle);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //val view = inflater.inflate(R.layout.account_select, container)
        val binding: com.dingdonginc.zhangfang.databinding.AccountSelectBinding =
            DataBindingUtil.inflate(inflater, R.layout.account_select, container, false)
        viewModel = ViewModelProviders.of(this).get(SelectDialogViewModel::class.java)
        binding.setVm(viewModel)
        //mImg_close = view.findViewById(com.dingdonginc.zhangfang.R.id.not_winning_close)
        //mImg_close.setOnClickListener(this)
        return binding.root
    }
}