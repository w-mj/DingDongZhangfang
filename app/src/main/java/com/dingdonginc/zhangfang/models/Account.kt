package com.dingdonginc.zhangfang.models

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.time.LocalDateTime

/**
 * 账目表
 */
@DatabaseTable(tableName = "account")
class Account {
    @DatabaseField(columnName = "id", id = true, generatedId = true)
    var id = 0;
    @DatabaseField(columnName = "method")
    lateinit var method: Method  // 付款方式
    @DatabaseField(columnName = "time")
    lateinit var time: LocalDateTime  // 付款时间
    @DatabaseField(columnName = "location")
    var location = 0  // TODO: 付款地点
    @DatabaseField(columnName = "partner")
    lateinit var partner: String  // 交易对方
    @DatabaseField(columnName = "tag")
    lateinit var tag: Tag  // 标签
    @DatabaseField(columnName = "comment")
    lateinit var comment: String  // 注释

}