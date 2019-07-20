package com.dingdonginc.zhangfang.layoutservice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.PagerAdapter
import java.lang.Exception

class WalletViewPagerAdapter(
    private val mList: List<ViewModel>,
    private val varId: Int,
    private val layoutId: Int,
    private val layoutInflater: LayoutInflater
): PagerAdapter() {
    /**
     * Return the number of views available.
     */
    override fun getCount(): Int {
        return mList.size
    }

    /**
     * Determines whether a page View is associated with a specific key object
     * as returned by [.instantiateItem]. This method is
     * required for a PagerAdapter to function properly.
     *
     * @param view Page View to check for association with `object`
     * @param object Object to check for association with `view`
     * @return true if `view` is associated with the key object `object`
     */
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        val binding : ViewDataBinding = `object` as ViewDataBinding
        return view == binding.root
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "真实"
            1 -> "虚拟"
            else -> {
                throw Exception("position must be 1 or 0")
            }
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding: ViewDataBinding = DataBindingUtil.inflate(layoutInflater, layoutId, container, true)
        binding.setVariable(varId, mList[position])
        return binding
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val binding : ViewDataBinding = `object` as ViewDataBinding
        container.removeView(binding.root)
    }
}