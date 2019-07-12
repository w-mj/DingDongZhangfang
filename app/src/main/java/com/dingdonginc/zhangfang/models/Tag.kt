package com.dingdonginc.zhangfang.models

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * 账目标签表
 */
@DatabaseTable(tableName = "tag")
class Tag {
    @DatabaseField(generatedId = true)
    var id = 0
    @DatabaseField
    lateinit var name: String  // 标签名称
    @DatabaseField
    var icon = 1  // TODO:标签图标数据类型
    @DatabaseField
    lateinit var comment: String  // 注释
}