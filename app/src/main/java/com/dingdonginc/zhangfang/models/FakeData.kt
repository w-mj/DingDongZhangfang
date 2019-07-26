package com.dingdonginc.zhangfang.models

import android.util.Log
import java.util.*


class FakeData {
    private var Tag:String ?= null
    private var walletType = arrayListOf<String>()
    private val time = Date()
    private val amount = 0

    fun FakeData(){
        walletType = arrayListOf("支付宝余额","微信余额","蚂蚁花呗","东北大学校园卡")
    }
    //随机获取时间
    fun RandomDate():Date {
        val calendar = Calendar.getInstance()
        //月份从0开始算
        calendar.set(2019, 0, 1)
        calendar.getTime().getTime()
        //时分秒设置为0
        calendar.set(Calendar.HOUR_OF_DAY,0)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)
        val minDate = calendar.getTime().getTime()//设置最小时间

        calendar.set(2019, 8, 1)
        calendar.set(Calendar.HOUR_OF_DAY,0)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)
        val maxDate = calendar.getTime().getTime()

        //得到在min-max中间的时间
        val randomDate = Math.random()*(maxDate-minDate)+minDate
        //变成long型
        calendar.setTimeInMillis(Math.round(randomDate))

        //Log.i("!!!!!!Time",calendar.getTime().toString())
        return calendar.getTime()
    }

    //随机获取Tag
    fun RandomTag():String{
        var  TagList = arrayListOf("服装","日常消费","日用品","餐饮","宠物","购物","食物","学习","摄影","交通","旅行",
            "吸烟？","水果？","居住","美容","健康","聚会","未指定")
        Log.i("TagSize",TagList.size.toString())
        var i = ((Math.random()*TagList.size)+1).toInt()
        Tag = TagList[i]
        return TagList[i]
    }





}