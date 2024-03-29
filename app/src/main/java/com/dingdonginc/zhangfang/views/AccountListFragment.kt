package com.dingdonginc.zhangfang.views

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.viewmodels.AccountListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AccountListFragment : Fragment() {
    private lateinit var viewModel: AccountListViewModel
    private lateinit var binding: com.dingdonginc.zhangfang.databinding.AppBarMainBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.app_bar_main, container, false)
        viewModel = ViewModelProviders.of(this).get(AccountListViewModel::class.java)
        binding.let {
            it.vm = viewModel
            it.lifecycleOwner = this
        }
        return binding.root
    }
}