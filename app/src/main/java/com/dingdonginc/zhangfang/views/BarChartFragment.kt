package com.dingdonginc.zhangfang.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.dingdonginc.zhangfang.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
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
        // chart.setHighlightEnabled(false);

        chart!!.setDrawBarShadow(false)

        chart!!.setDrawValueAboveBar(true)

        chart!!.getDescription().isEnabled = false

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart!!.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately
        chart!!.setPinchZoom(false)

        // draw shadows for each bar that show the maximum value
        // chart.setDrawBarShadow(true);

        chart!!.setDrawGridBackground(false)

        //set xAxis
        val formatter = MyFormatterNew(months)
        val xl = chart!!.getXAxis()
        xl.valueFormatter = formatter
        xl.position = XAxis.XAxisPosition.BOTTOM
        xl.setDrawAxisLine(true)
        xl.setDrawLabels(true)
        xl.setDrawGridLines(false)
        xl.labelCount = 12
        xl.granularity = 10f

        val yl = chart!!.getAxisLeft()
        yl.setDrawAxisLine(true)
        yl.setDrawGridLines(true)
        yl.axisMinimum = 0f // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        val yr = chart!!.getAxisRight()
        yr.setDrawAxisLine(true)
        yr.setDrawGridLines(false)
        yr.axisMinimum = 0f // this replaces setStartAtZero(true)
//        yr.setInverted(true);

        chart!!.setFitBars(true)
        chart!!.animateY(2500)


        val l = chart!!.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.formSize = 8f
        l.xEntrySpace = 4f

        setData(50f)
        return root
    }

    private fun setData(range: Float) {

        val barWidth = 9f
        val spaceForBar = 10f
        val values = ArrayList<BarEntry>()

        for (i in months.indices) {
            val `val` = (Math.random() * range).toFloat()
            values.add(BarEntry(i * spaceForBar, `val`))
        }

        val set1: BarDataSet

        if (chart!!.getData() != null && chart!!.getData().dataSetCount > 0) {
            set1 = chart!!.getData().getDataSetByIndex(0) as BarDataSet
            set1.values = values
            chart!!.getData().notifyDataChanged()
            chart!!.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "DataSet 1")
            set1.setColors(*ColorTemplate.VORDIPLOM_COLORS)
            set1.setDrawIcons(false)

            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)

            val data = BarData(dataSets)
            data.setValueTextSize(10f)

            data.barWidth = barWidth
            chart!!.setData(data)
        }
    }

}

