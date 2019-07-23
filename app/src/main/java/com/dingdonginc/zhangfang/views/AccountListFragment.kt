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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val fab = view!!.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val itent = Intent()
            itent.setClass(context!!, AddAccountActivity::class.java)
            startActivity(itent);
            activity!!.finish()
        }
        val sbt = view!!.findViewById<Button>(R.id.selectbt)
        sbt.setOnClickListener{
            click()
        }
//        var list = ArrayList<String>()
//        list.add("白羊座")
//        list.add("金牛座")
//        list.add("双子座")
//        list.add("巨蟹座")
//        list.add("狮子座")
//        list.add("处女座")
//        list.add("天秤座")
//        list.add("天蝎座")
//        list.add("射手座")
//        list.add("摩羯座")
//        list.add("水瓶座")
//        list.add("双鱼座")
//
//        var adapter = ArrayAdapter<String>(this.context!!,R.layout.spinner_item_selected,list)
//        adapter.setDropDownViewResource(R.layout.spinner_item_drop)
//        var spiner = view!!.findViewById<Spinner>(R.id.spin)
//        Log.d(R.id.spin.toString(), R.id.spin.toString())
//        spiner.setAdapter(adapter)
//        spiner = view!!.findViewById(R.id.spin2)
//        spiner.setAdapter(adapter)
//        spiner = view!!.findViewById(R.id.spin3)
//        spiner.setAdapter(adapter)
//        spiner = view!!.findViewById(R.id.spin4)
//        spiner.setAdapter(adapter)
    }

    fun click(){
        var selectDialog: SelectDialog = SelectDialog()
        selectDialog.show(getFragmentManager(),"lose")
    }
}