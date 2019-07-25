package com.dingdonginc.zhangfang.services

import android.app.Activity
import android.content.Intent

class ActivityService {
    var activity: Activity? = null

    fun <T: Activity>switchActivity(to: Class<T>) {
        val intent = Intent()
        intent.setClass(activity!!, to)
        activity!!.startActivity(intent)
        activity!!.finish()
    }
}