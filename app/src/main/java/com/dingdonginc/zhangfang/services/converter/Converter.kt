package com.dingdonginc.zhangfang.services.converter

import com.dingdonginc.zhangfang.models.Account
import com.dingdonginc.zhangfang.models.DayAccounts
import com.dingdonginc.zhangfang.models.Tag
import java.util.*
import kotlin.collections.ArrayList

object Converter {
    fun AccListToDayAccList(accounts : ArrayList<Account>) : ArrayList<DayAccounts>{
        var dayAccountsList = ArrayList<DayAccounts>()
        var lastTime : Date //= accList[0].time
        var partofAcc = ArrayList<Account>()
        var accList = accounts
        var input : Float = 0F
        var output : Float = 0F
        //按时间升序排序
        accList.sortByDescending { it.time  }
        //按日期分组
        if(accList.size != 0) {
            lastTime = accList[0].time
            for (acc in accList) {
                if (acc.time.date == lastTime.date &&
                    acc.time.month == lastTime.month &&
                    acc.time.year == lastTime.year)
                    partofAcc.add(acc)
                else {
                    dayAccountsList.add(DayAccounts(partofAcc, DateIntToString(lastTime.date),
                        DayIntToString(lastTime.day), input, output))
                    lastTime = acc.time
                    partofAcc = ArrayList<Account>()
                    input = 0F
                    output = 0F
                    partofAcc.add(acc)
                }
                if(acc.amount > 0)
                    input += acc.amount/100F
                else
                    output += -acc.amount/100F
            }
            dayAccountsList.add(DayAccounts(partofAcc,  DateIntToString(lastTime.date),
                DayIntToString(lastTime.day),input,output))
        }
        return dayAccountsList
    }

    fun DateIntToString(date : Int): String {
        return date.toString() + "日"
    }

    fun DayIntToString(day : Int) : String{
        return when(day){
            0 -> "· 星期日"
            1 -> "· 星期一"
            2 -> "· 星期二"
            3 -> "· 星期三"
            4 -> "· 星期四"
            5 -> "· 星期五"
            6 -> "· 星期六"
            else -> "· undefined"
        }
    }

    fun GroupTagList(tagList: List<Tag>): ArrayList<ArrayList<Tag>>{
        var count = 0
        var tags = ArrayList<Tag>()
        val tagGroup = ArrayList<ArrayList<Tag>>()
        for (tag in tagList){
            tags.add(tag)
            count++
            if(count == 10){
                tagGroup.add(tags)
                count = 0
                tags = ArrayList<Tag>()
            }
        }
        if(tags.size != 0)
            tagGroup.add(tags)
        return tagGroup
    }
}

