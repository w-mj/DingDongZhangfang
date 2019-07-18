package com.dingdonginc.zhangfang.services

import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.models.DatabaseHelper
import com.dingdonginc.zhangfang.models.Wallet
import com.dingdonginc.zhangfang.models.WalletType
import com.j256.ormlite.dao.Dao
import org.kodein.di.generic.instance

class WalletService {

    private fun getDao(): Dao<Wallet, *> {
        val helper: DatabaseHelper by App.getKodein().instance()
        return helper.getDao(Wallet::class.java)
    }

    fun getAll(): List<Wallet>
            = getDao().queryForAll()

    fun getPredefined(): List<Wallet>
            = getDao().queryBuilder().where().ge(Wallet::predefined.name, true).query()

    fun getUserDefined(): List<Wallet>
            = getDao().queryBuilder().where().ge(Wallet::predefined.name, false).query()

    fun getVirtual(): List<Wallet>
            = getDao().queryBuilder().where().ge(Wallet::type.name, WalletType.Virtual).query()

    fun getReal(): List<Wallet>
            = getDao().queryBuilder().where().ge(Wallet::type.name, WalletType.Real).query()

    fun addWallet(wallet: Wallet): Int
            = getDao().create(wallet)
}