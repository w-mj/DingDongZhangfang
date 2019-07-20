package com.dingdonginc.zhangfang.views

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.dingdonginc.zhangfang.BR

import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.databinding.WalletFragmentBinding
import com.dingdonginc.zhangfang.layoutservice.WalletViewPagerAdapter
import com.dingdonginc.zhangfang.viewmodels.WalletViewModel
import com.google.android.material.tabs.TabLayout

class WalletFragment : Fragment() {


    companion object {
        fun newInstance() = WalletFragment()
    }

//    private lateinit var viewModel: WalletViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.wallet_fragment, container, false)
//        binding = DataBindingUtil.inflate(inflater, R.layout.wallet_fragment, container, false)
//        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(WalletViewModel::class.java)

        val pager = view!!.findViewById<ViewPager>(R.id.wallet_pager)
        val lst = ArrayList<WalletViewModel>()
//        lst.add(ViewModelProviders.of(this).get(WalletViewModel::class.java))
//        lst.add(ViewModelProviders.of(this).get(WalletViewModel::class.java))
        lst.add(WalletViewModel())
        lst.add(WalletViewModel())
        val adapter = WalletViewPagerAdapter(lst, BR.vm, R.layout.wallet_list_layout, layoutInflater)
        pager.adapter = adapter

        val tab = view!!.findViewById<TabLayout>(R.id.wallet_tab)
        tab.setupWithViewPager(pager)
    }

}
