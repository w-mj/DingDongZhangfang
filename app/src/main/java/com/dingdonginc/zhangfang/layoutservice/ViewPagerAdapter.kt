package com.dingdonginc.zhangfang.layoutservice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewpager.widget.PagerAdapter
import com.dingdonginc.zhangfang.BR
import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.databinding.DayAccountsBinding
import com.dingdonginc.zhangfang.viewmodels.AddAccountViewModel

class ViewPagerAdapter<T>(var viewList : List<List<T>>,var vm : AddAccountViewModel,
                          val varId1 : Int, val layoutId : Int, val layoutInflater: LayoutInflater) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        val binding : ViewDataBinding = `object` as ViewDataBinding
        return view == binding.getRoot()
    }

    override fun getCount(): Int {
        return viewList.size
    }
    //初始化ViewPager各页面
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding : ViewDataBinding = DataBindingUtil.inflate(layoutInflater, layoutId, container, true)
        binding.setVariable(varId1, viewList.get(position))
        binding.setVariable(BR.addvm, vm)
        return binding
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val binding : ViewDataBinding = `object` as ViewDataBinding
        container.removeView(binding.getRoot())
    }
}