package com.dingdonginc.zhangfang.services.neuspider

import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.models.Tag
import com.dingdonginc.zhangfang.models.TagFactory
import org.kodein.di.generic.instance

class NEUWallet(
    val time: String,
    val description: String,
    val amount: String,
    val remain: String,
    val operator: String,
    val station: String,
    val terminal: String
){
    override fun toString(): String
            = "时间$time 描述$description 金额$amount 余额$remain 操作员$operator 工作站$station 终端$terminal"

    fun getTag(): Tag {
        val tagFactory: TagFactory by App.getKodein().instance()
        return tagFactory.getPredefined(
            when(description) {
                "餐费支出"->TagFactory.Type.Food
                "商场购物"->TagFactory.Type.Shopping
                "淋浴支出"->TagFactory.Type.Shower
                "水果店消费支出"->TagFactory.Type.Fruit
                "公交支出"->TagFactory.Type.Transport
                "用电支出"->TagFactory.Type.DailyCost
                else->TagFactory.Type.Unknown
            }
        )
    }
}
