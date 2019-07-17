package com.dingdonginc.zhangfang.services

import android.content.Context
import com.dingdonginc.zhangfang.models.DatabaseHelper
import com.dingdonginc.zhangfang.models.Wallet
import com.dingdonginc.zhangfang.models.WalletType

object WalletService {
    private fun getDao(context: Context?)
            = DatabaseHelper.getHelper(context).getDao(Wallet::class.java)

    fun getAll(context: Context? =null): List<Wallet>
            = getDao(context).queryForAll()

    fun getPredefined(context: Context? =null): List<Wallet>
            = getDao(context).queryBuilder().where().ge(Wallet::predefined.name, true).query()

    fun getUserDefined(context: Context? =null): List<Wallet>
            = getDao(context).queryBuilder().where().ge(Wallet::predefined.name, false).query()

    fun getVirtual(context: Context? =null): List<Wallet>
            = getDao(context).queryBuilder().where().ge(Wallet::type.name, WalletType.Virtual).query()

    fun getReal(context: Context? =null): List<Wallet>
            = getDao(context).queryBuilder().where().ge(Wallet::type.name, WalletType.Real).query()

    fun addWallet(context: Context? = null, wallet: Wallet): Int
            = getDao(context).create(wallet)
}