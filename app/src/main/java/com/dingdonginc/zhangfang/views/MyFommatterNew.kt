package com.dingdonginc.zhangfang.views

import android.util.Log
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

/**
 * 格式化x轴数据，可以显示字符串
 */
class MyFormatterNew(private val mValues: Array<String>) : ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        Log.d(TAG, "------>getAxisLable$value")
        return mValues[value.toInt() % mValues.size]
    }

    companion object {
        private val TAG = "MyXFormatter"
    }

}
