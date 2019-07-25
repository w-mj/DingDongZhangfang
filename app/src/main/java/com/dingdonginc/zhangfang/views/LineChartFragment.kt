package com.dingdonginc.zhangfang.views


import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dingdonginc.zhangfang.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import java.util.ArrayList


class LineChartFragment : Fragment() {

    private var chart: LineChart? = null
    //X轴显示月份
    private val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.activity_linechart, container, false)

        run {
            // // Chart Style // //
            chart = root.findViewById(R.id.linechart)

            // background color
            chart!!.setBackgroundColor(Color.WHITE)

            // disable description text
            chart!!.description.isEnabled = false

            // enable touch gestures
            chart!!.setTouchEnabled(true)

            // set listeners
            chart!!.setDrawGridBackground(false)

            // create marker to display box when values are selected
           // val mv = MyMarkerView(this, R.layout.custom_marker_view)
            // Set the marker to the chart
           // mv.setChartView(chart)
            //chart!!.marker = mv

            // enable scaling and dragging
            chart!!.isDragEnabled = true
            chart!!.setScaleEnabled(true)
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            chart!!.setPinchZoom(true)
        }

        val xAxis: XAxis
        run {
            val formatter = MyFormatterNew(months)
            // // X-Axis Style // //
            xAxis = chart!!.xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f)
            //COUNT
            xAxis.labelCount = 12
            xAxis.valueFormatter = formatter
        }

        val yAxis: YAxis
        run {
            // // Y-Axis Style // //
            yAxis = chart!!.axisLeft

            // disable dual axis (only use LEFT axis)
            chart!!.axisRight.isEnabled = false

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f)

            // axis range
            yAxis.axisMaximum = 200f
            yAxis.axisMinimum = 0f
        }


        run {
            // // Create Limit Lines // //
            val llXAxis = LimitLine(9f, "Index 10")
            llXAxis.lineWidth = 4f
            llXAxis.enableDashedLine(10f, 10f, 0f)
            llXAxis.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
            llXAxis.textSize = 10f


            val ll1 = LimitLine(150f, "Upper Limit")
            ll1.lineWidth = 4f
            ll1.enableDashedLine(10f, 10f, 0f)
            ll1.labelPosition = LimitLabelPosition.RIGHT_TOP
            ll1.textSize = 10f


            val ll2 = LimitLine(-30f, "Lower Limit")
            ll2.lineWidth = 4f
            ll2.enableDashedLine(10f, 10f, 0f)
            ll2.labelPosition = LimitLabelPosition.RIGHT_BOTTOM
            ll2.textSize = 10f


            // draw limit lines behind data instead of on top
            yAxis.setDrawLimitLinesBehindData(true)
            xAxis.setDrawLimitLinesBehindData(true)

            // add limit lines
            yAxis.addLimitLine(ll1)
            yAxis.addLimitLine(ll2)
            //xAxis.addLimitLine(llXAxis);
        }

        // add data
        setData()

        // draw points over time
        chart!!.animateX(1500)

        // get the legend (only possible after setting data)
        val l = chart!!.legend

        // draw legend entries as lines
        l.form = LegendForm.LINE

       return root
    }

    private fun setData() {

        val values = ArrayList<Entry>()

        for (i in months.indices) {
            //数据替换这里
            val `val` = (Math.random() * 180).toFloat() - 30
        }

        val set1: LineDataSet

        if (chart!!.data != null && chart!!.data.dataSetCount > 0) {
            set1 = chart!!.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            chart!!.data.notifyDataChanged()
            chart!!.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")

            set1.setDrawIcons(false)

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 3f

            // draw points as solid circles
            set1.setDrawCircleHole(false)

            // customize legend entry
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            // text size of values
            set1.valueTextSize = 9f

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter = IFillFormatter { dataSet, dataProvider -> chart!!.axisLeft.axisMinimum }

            // set color of filled area
           /* if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                val drawable = ContextCompat.getDrawable(this, R.drawable.fade_red)
                set1.fillDrawable = drawable
            } else {*/
            set1.fillColor = Color.TRANSPARENT


            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            chart!!.data = data
        }
    }

/*
    override fun onValueSelected(e: Entry, h: Highlight) {
        Log.i("Entry selected", e.toString())
        Log.i("LOW HIGH", "low: " + chart!!.lowestVisibleX + ", high: " + chart!!.highestVisibleX)
        Log.i(
            "MIN MAX",
            "xMin: " + chart!!.xChartMin + ", xMax: " + chart!!.xChartMax + ", yMin: " + chart!!.yChartMin + ", yMax: " + chart!!.yChartMax
        )
    }

    override fun onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.")
    }
    */
}
