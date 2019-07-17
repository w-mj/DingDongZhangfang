package com.dingdonginc.zhangfang.services

import android.content.Context
import com.dingdonginc.zhangfang.models.Account
import com.dingdonginc.zhangfang.models.DatabaseHelper

object AccountService {
    fun getDao(context: Context?=null) = DatabaseHelper.getHelper(context).getDao(Account::class.java)

    fun getAll(context: Context?=null): List<Account> = getDao(context).queryForAll()

    fun addAccount(context: Context?=null, account: Account): Int{
        val dao = getDao(context)
        if (account.generatedId == null)
            return dao.create(account)
        // 已经添加过
        if (dao.queryForEq(Account::generatedId.name, account.generatedId).size >= 1)
            return 0
        return dao.create(account)
    }
}