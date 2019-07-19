package com.dingdonginc.zhangfang.models

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

/**
 * 账目表
 */
@DatabaseTable(tableName = "account")
class Account (){
    init { }
    constructor(
        wallet: Wallet,
        amount: Int,
        tag: Tag,
        time: Date,
        longitude: Double = Double.MAX_VALUE,
        latitude: Double = Double.MAX_VALUE,
        partner: String = "",
        comment: String = "",
        generatedId: String?=null
    ) : this() {
        this.wallet = wallet
        this.amount = amount
        this.tag = tag
        this.time = time
        this.longitude = longitude
        this.latitude = latitude
        this.partner = partner
        this.comment = comment
        this.generatedId = generatedId
    }
    @DatabaseField(generatedId = true)
    var id = 0
    @DatabaseField(foreign = true, foreignAutoRefresh=true)
    lateinit var wallet: Wallet  // 付款方式
    @DatabaseField
    var amount = 0  // 金额，以分为单位（+为收入，-为支出）
    @DatabaseField(columnName = "DATUM_LA", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    lateinit var time: Date  // 付款时间
    @DatabaseField
    var longitude = 0.0  // 经度
    @DatabaseField
    var latitude = 0.0  // 纬度
    @DatabaseField(defaultValue = "")
    lateinit var partner: String  // 交易对方
    @DatabaseField(foreign = true, foreignAutoRefresh=true)
    lateinit var tag: Tag  // 标签
    @DatabaseField(defaultValue = "")
    lateinit var comment: String  // 注释
    @DatabaseField(canBeNull = true, index = true)
    var generatedId: String? = null  // 由付款软件生成的订单号


    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("id:").append(id).append('\n');
        sb.append("wallet:").append(wallet.name).append('\n')
        sb.append("amount:").append(amount).append('\n')
        val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA)
        sb.append("time:").append(formatter.format(time)).append('\n')
        sb.append("partner:").append(partner).append('\n')
        sb.append("tag:").append(tag.name).append('\n')
        sb.append("comment:").append(comment).append('\n')
        sb.append("generatedId:").append(generatedId).append('\n')
        return sb.toString()
    }
}