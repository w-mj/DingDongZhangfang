package com.dingdonginc.zhangfang.models

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

enum class MethodType{
    Real, Virtual
}

/**
 * 数据库中的付（收）款方式表
 */
@DatabaseTable(tableName = "method")
class Method {
    @DatabaseField(id=false, generatedId = true)
    private var id: Int = 0
    @DatabaseField
    lateinit var name: String  // 方式名称
    @DatabaseField
    lateinit var type: MethodType  // 方式类型（真实，虚拟）
    @DatabaseField
    lateinit var comment: String  // 注释
    @DatabaseField(defaultValue = "false")
    var predefined = false  // 是否为预定义的方式
    @DatabaseField(defaultValue = "false")
    var hidden = false  // 是否为已删除的方式
}