package com.dingdonginc.zhangfang.services.neuspider

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
}