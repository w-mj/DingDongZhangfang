package com.dingdonginc.zhangfang.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.models.Account

class InfoDialog(val acc: Account): DialogFragment() {
//    private lateinit var viewModel: SelectDialogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MyMiddleDialogStyle);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //val view = inflater.inflate(R.layout.account_select, container)
        val binding: com.dingdonginc.zhangfang.databinding.InfoDialogBinding =
            DataBindingUtil.inflate(inflater, R.layout.info_dialog, container, false)
        //viewModel = ViewModelProviders.of(this).get(SelectDialogViewModel::class.java)
        //binding.setVm(viewModel)
        binding.setAcc(acc)
        return binding.root
    }
}