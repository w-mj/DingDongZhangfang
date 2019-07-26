package com.dingdonginc.zhangfang.views

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.dingdonginc.zhangfang.BR

import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.layoutservice.ViewPagerAdapter
import com.dingdonginc.zhangfang.models.Tag
import com.dingdonginc.zhangfang.viewmodels.AddAccountViewModel

class AddAccountFragment : Fragment() {
    init {
        Log.i("AddAccountFragment", "init")
    }

    companion object {
        fun newInstance() = AddAccountFragment()
    }

    private lateinit var viewModel: AddAccountViewModel
    private lateinit var binding: com.dingdonginc.zhangfang.databinding.AddAccountFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("AddAccountFragment", "onCreateView")

        binding = DataBindingUtil.inflate(inflater, R.layout.add_account_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(AddAccountViewModel::class.java)
        binding.vm = viewModel
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val viewPager : ViewPager = view!!.findViewById(R.id.typeview)
        val adapter = ViewPagerAdapter(viewModel.typeList, viewModel, BR.tag , R.layout.typelist_item, getLayoutInflater())
        viewPager.adapter = adapter
    }
}
