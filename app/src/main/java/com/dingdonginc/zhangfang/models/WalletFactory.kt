package com.dingdonginc.zhangfang.models

import android.util.Log
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.R
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.stmt.query.In
import org.kodein.di.generic.instance

/**
 * 钱包工厂函数
 */
class WalletFactory {
    private var idCache: HashMap<String, Int> = HashMap()

    private fun generateWallet(name: String, type: WalletType, icon: Int): Wallet {
        val w = Wallet()
        w.balance = 0
        w.comment = name + "账户"
        w.hidden = false
        w.name = name
        w.predefined = true
        w.type = type
        w.icon = icon
        return w
    }

    private fun getWallet(name: String, type: WalletType, icon: Int): Wallet {
        Log.i("getWallet", name)
        val helper: DatabaseHelper by App.getKodein().instance()
        val dao = helper.getDao(Wallet::class.java)
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
                val w = generateWallet(name, type, icon)
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

    enum class Type {
        AlipayBalance, WechatBalance, Huabei
    }
    private val predefineIcon = hashMapOf(
        Type.AlipayBalance to R.mipmap.zfb,
        Type.WechatBalance to R.mipmap.wechat,
        Type.Huabei to R.mipmap.huabei
    )
    private val predefinedWallet = hashMapOf(
        Type.AlipayBalance to "支付宝余额",
        Type.WechatBalance to "微信余额",
        Type.Huabei to "蚂蚁花呗"
    )
    private val predefinedWalletType = mapOf(
        Type.AlipayBalance to WalletType.Real,
        Type.WechatBalance to WalletType.Real,
        Type.Huabei to WalletType.Virtual
    )

    fun insertPredefinedToDb(dao: Dao<Wallet, Int>) {
        for (w in predefinedWallet){
            getWallet(w.value, predefinedWalletType[w.key]!!, predefineIcon[w.key]!!)
        }
    }

    private fun getPredefined(name: Type): Wallet {
        return getWallet(predefinedWallet.getValue(name),
            predefinedWalletType.getValue(name),
            predefineIcon.getValue(name)
        )
    }

    fun alipayBalance() = getPredefined(Type.AlipayBalance)

    fun wechatBalance() = getPredefined(Type.WechatBalance)

    fun huabei() = getPredefined(Type.Huabei)

}