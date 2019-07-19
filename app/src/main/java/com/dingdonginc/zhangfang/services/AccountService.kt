package com.dingdonginc.zhangfang.services

import android.content.Context
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.models.Account
import com.dingdonginc.zhangfang.models.DatabaseHelper
import com.j256.ormlite.dao.Dao
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class AccountService {
    fun getDao(): Dao<Account, *> {
        val helper: DatabaseHelper by App.getKodein().instance()
        return helper.getDao(Account::class.java)
    }

    fun getAll(): List<Account> = getDao().queryForAll()

    fun addAccount(account: Account): Int{
        val dao = getDao()
        if (account.generatedId == null)
            return dao.create(account)
        // 已经添加过
        if (dao.queryForEq(Account::generatedId.name, account.generatedId).size >= 1)
            return 0
        return dao.create(account)
    }
}