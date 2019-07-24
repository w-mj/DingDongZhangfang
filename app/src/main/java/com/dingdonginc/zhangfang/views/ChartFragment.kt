package com.dingdonginc.zhangfang.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.dingdonginc.zhangfang.layoutservice.ChartFragmentAdapter
import com.google.android.material.tabs.TabLayout


class ChartFragment : Fragment() {

        private var tabLayout: TabLayout? = null
        private var viewPager: ViewPager? = null
       // private var mFragments: ArrayList<Fragment> = ArrayList()
      //  private var adapter: ChartFragment? = null
        //private val titles = arrayOf("饼状图", "线状图", "柱状图")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(com.dingdonginc.zhangfang.R.layout.chart_main_fragment, container, false)

            viewPager = root.findViewById(com.dingdonginc.zhangfang.R.id.viewpager) as ViewPager
            tabLayout = root.findViewById(com.dingdonginc.zhangfang.R.id.tablayout) as TabLayout

            //设置适配器
           // adapter = ChartFragment(fragmentManager!!)
            //viewPager!!.adapter = adapter
            //adapter.addFragments(mFragments)getChildFragmentManager
        //val mAdapter = ChartFragmentAdapter(fragmentManager!!)
            val mAdapter = ChartFragmentAdapter(childFragmentManager)
            viewPager!!.adapter = mAdapter
            //viewPager!!.setAdapter(mAdapter)
            viewPager!!.setCurrentItem(1)
            viewPager!!.setOffscreenPageLimit(3)

            //必须放tab前
            //必须先清除再手动添
            tabLayout!!.setupWithViewPager(viewPager)
            tabLayout!!.removeAllTabs()

            //设置tab
            tabLayout!!.addTab(tabLayout!!.newTab().setText("饼状图"))
            tabLayout!!.addTab(tabLayout!!.newTab().setText("线状图"))
            tabLayout!!.addTab(tabLayout!!.newTab().setText("柱状图"))

            //设置页面
/*          mFragments = ArrayList()
            mFragments.add(PieChartFragment())
            mFragments.add(LineChartFragment())
            mFragments.add(PieChartFragment())

*/
        return root
        }


    }


