package com.dingdonginc.zhangfang.services

import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.models.Account
import com.dingdonginc.zhangfang.models.DatabaseHelper
import com.j256.ormlite.dao.Dao
import org.kodein.di.generic.instance

class AccountService(private val tagService: TagService, private val walletService: WalletService) {
    fun getDao(): Dao<Account, *> {
        val helper: DatabaseHelper by App.getKodein().instance()
        return helper.getDao(Account::class.java)
    }

    fun getAll(): List<Account> {
        val tagDao = tagService.getDao()
        val walletDao = walletService.getDao()
        val list = getDao().queryForAll()
        list.forEach {
            tagDao.refresh(it.tag)
            walletDao.refresh(it.wallet)
        }
        return list
    }

    fun addAccount(account: Account): Int{
        val dao = getDao()
        // 已经添加过
        if (account.generatedId != null &&
            dao.queryForEq(Account::generatedId.name, account.generatedId).size >= 1)
            return 0
        account.wallet.balance -= account.amount
        val walletService: WalletService by App.getKodein().instance()
        walletService.update(account.wallet)
        return dao.create(account)
    }
}