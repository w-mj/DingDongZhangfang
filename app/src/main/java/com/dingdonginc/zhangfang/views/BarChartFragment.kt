package com.dingdonginc.zhangfang.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.dingdonginc.zhangfang.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.ArrayList

class BarChartFragment : Fragment(){

        private var chart: BarChart? = null

        //X轴显示月份
        private val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.activity_barchart, container, false)

            chart = root.findViewById(R.id.barchart)
            setData()

            chart!!.description.isEnabled = false
            chart!!.setDrawValueAboveBar(true)

            // scaling can now only be done on x- and y-axis separately
            chart!!.setPinchZoom(false)
            chart!!.isDoubleTapToZoomEnabled = false

            //value will show on the top
            chart!!.setDrawValueAboveBar(true)

            chart!!.setDrawBarShadow(false)
            chart!!.setDrawGridBackground(false)

            val formatter = MyFormatterNew(months)
            val xAxis = chart!!.xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.labelCount = 12
            xAxis.valueFormatter = formatter

            chart!!.axisLeft.setDrawGridLines(false)

            // add a nice and smooth animation
            chart!!.animateY(1500)

            chart!!.legend.isEnabled = false

        return root
        }


        private fun setData() {

            val values = ArrayList<BarEntry>()

            for (i in months.indices) {
                val maxY = 10000.toFloat()
                val `val` = (Math.random() * maxY).toFloat() + maxY / 3
                values.add(BarEntry(i.toFloat(), `val`))
            }

            val set1: BarDataSet

            if (chart!!.data != null && chart!!.data.dataSetCount > 0) {
                set1 = chart!!.data.getDataSetByIndex(0) as BarDataSet
                set1.values = values
                chart!!.data.notifyDataChanged()
                chart!!.notifyDataSetChanged()
            } else {
                set1 = BarDataSet(values, "Data Set")
                set1.setColors(*ColorTemplate.VORDIPLOM_COLORS)
                set1.setDrawValues(false)

                val dataSets = ArrayList<IBarDataSet>()
                dataSets.add(set1)

                val data = BarData(dataSets)
                chart!!.data = data
                chart!!.setFitBars(true)
            }

            chart!!.invalidate()
        }


    }

