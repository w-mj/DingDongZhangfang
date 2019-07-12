package com.dingdonginc.zhangfang.models

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

/**
 * 账目表
 */
@DatabaseTable(tableName = "account")
class Account {
    @DatabaseField(generatedId = true)
    var id = 0;
    @DatabaseField(foreign = true)
    lateinit var method: Method  // 付款方式
    @DatabaseField(columnName = "DATUM_LA", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    lateinit var time: Date  // 付款时间
    @DatabaseField
    var longitude = 0.0  // 经度
    @DatabaseField
    var latitude = 0.0  // 纬度
    @DatabaseField
    lateinit var partner: String  // 交易对方
    @DatabaseField(foreign = true)
    lateinit var tag: Tag  // 标签
    @DatabaseField
    lateinit var comment: String  // 注释
}