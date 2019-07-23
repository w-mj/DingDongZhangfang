package com.dingdonginc.zhangfang.models

import com.dingdonginc.zhangfang.R
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

enum class WalletType{
    Real, Virtual
}

/**
 * 数据库中的付（收）款方式表
 */
@DatabaseTable(tableName = "wallet")
class Wallet() {
    constructor(
        name: String,
        type: WalletType,
        comment: String="",
        predefined: Boolean=false,
        icon: Int = R.drawable.baseline_sentiment_very_satisfied_black_36
    ): this() {
        this.name = name
        this.type = type
        this.comment = comment
        this.predefined = predefined
        this.icon = icon
    }
    @DatabaseField(id=false, generatedId = true)
    var id: Int = 0
    @DatabaseField
    lateinit var name: String  // 方式名称
    @DatabaseField
    lateinit var type: WalletType  // 方式类型（真实，虚拟）
    @DatabaseField(defaultValue = "")
    lateinit var comment: String  // 注释
    @DatabaseField(defaultValue = "false")
    var predefined = false  // 是否为预定义的方式
    @DatabaseField(defaultValue = "false")
    var hidden = false  // 是否为已删除的方式
    @DatabaseField      // 余额
    var balance = 0
    @DatabaseField
    var icon = 0
}