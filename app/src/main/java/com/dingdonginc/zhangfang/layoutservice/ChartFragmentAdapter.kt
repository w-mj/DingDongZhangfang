package com.dingdonginc.zhangfang.layoutservice


import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.dingdonginc.zhangfang.views.AddAccountFragment
import com.dingdonginc.zhangfang.views.BarChartFragment
import com.dingdonginc.zhangfang.views.LineChartFragment
import com.github.mikephil.charting.charts.BarChart
import com.test.chart.PieChartFragment
import java.lang.Exception

class ChartFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val PAGERCOUNT = 3

    private val myFragment1: PieChartFragment = PieChartFragment()
    private val myFragment2: LineChartFragment = LineChartFragment()
    private val myFragment3: BarChartFragment = BarChartFragment()
    //private var list: ArrayList<String>? = null

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> myFragment1
            1 -> myFragment2
            2 -> myFragment3
            else -> {
                throw Exception("fragment out of range")

            }
        }
    }

    override fun getCount(): Int {
        return PAGERCOUNT
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position)
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {

    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return super.isViewFromObject(view, `object`)
    }
    /*override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        println("position Destory$position")
    }
    */


}
