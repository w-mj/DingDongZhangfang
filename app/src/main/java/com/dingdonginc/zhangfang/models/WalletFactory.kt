package com.dingdonginc.zhangfang.models

import android.content.Context
import android.util.Log
import java.lang.Exception

/**
 * 钱包工厂函数
 */
object WalletFactory {
    private var idCache: HashMap<String, Int> = HashMap()

    private fun generateWallet(name: String, type: WalletType): Wallet {
        val w = Wallet()
        w.balance = 0
        w.comment = name + "账户"
        w.hidden = false
        w.name = name
        w.predefined = true
        w.type = type
        return w
    }

    private fun getWallet(name: String, type: WalletType, context: Context): Wallet {
        Log.i("getWallet", name)
        val dao = DatabaseHelper.getHelper(context).getDao(Wallet::class.java)
        val query = dao.queryBuilder()
        if (!idCache.containsKey(name)) {
            query.where()
                .eq("predefined", true).and()
                .eq("name", name)
        } else {
            query.where().eq("id", idCache[name])
        }
        val queryResult = query.query()
        when {
            queryResult.size == 0 -> {
                Log.i("getWallet", "Build $name balance")
                val w = generateWallet(name, type)
                dao.create(w)
                idCache[name] = w.id
                return w
            }
            queryResult.size == 1 -> {
                return queryResult[0]
            }
            else -> {
                Log.e("getWallet", "query $name more than 1")
                throw Exception()
            }
        }
    }

    private val predefinedWallet = mapOf(
        "支付宝余额" to WalletType.Real,
        "微信余额" to WalletType.Real
    )

    fun isPredefined(name: String) = name in predefinedWallet

    fun getPredefined(name: String, context: Context): Wallet {
        assert(name in predefinedWallet)
        return getWallet(name, predefinedWallet.getValue(name), context)
    }

    fun alipayBalance(context: Context) = getPredefined("支付宝余额", context)

    fun wechatBalance(context: Context) = getPredefined("微信余额", context)

}