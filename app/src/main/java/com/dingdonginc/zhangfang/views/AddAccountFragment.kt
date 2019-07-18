package com.dingdonginc.zhangfang.views

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.viewmodels.AddAccountViewModel
import com.dingdonginc.zhangfang.viewmodels.DigitalKeyboardViewModel

class AddAccountFragment : Fragment() {

    companion object {
        fun newInstance() = AddAccountFragment()
    }

    private lateinit var viewModel: AddAccountViewModel
    private lateinit var digital_vm: DigitalKeyboardViewModel
    private lateinit var binding: com.dingdonginc.zhangfang.databinding.AddAccountFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_account_fragment, container, false)
        digital_vm = ViewModelProviders.of(this).get(DigitalKeyboardViewModel::class.java)
        binding.digitalVm = digital_vm
//        return inflater.inflate(R.layout.add_account_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(AddAccountViewModel::class.java)

    }

}
