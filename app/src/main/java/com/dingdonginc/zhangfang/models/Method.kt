package com.dingdonginc.zhangfang.models

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

enum class MethodType{
    Real, Virtual
}

/**
 * 数据库中的付（收）款方式表
 */
@DatabaseTable(tableName = "method")
class Method{
    @DatabaseField(columnName = "id", id=true, generatedId = true)
    var id = 0
    @DatabaseField(columnName = "name")
    lateinit var name: String  // 方式名称
    @DatabaseField(columnName = "type")
    lateinit var type: MethodType  // 方式类型（真实，虚拟）
    @DatabaseField(columnName = "comment")
    lateinit var comment: String  // 注释
    @DatabaseField(columnName = "predefined", defaultValue = "false")
    var predefined = false  // 是否为预定义的方式
    @DatabaseField(columnName = "hidden", defaultValue = "false")
    var hidden = false  // 是否为已删除的方式
}