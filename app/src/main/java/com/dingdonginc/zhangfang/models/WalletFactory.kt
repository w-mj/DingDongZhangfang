package com.dingdonginc.zhangfang.models

import android.util.Log
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.R
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.stmt.query.In
import org.kodein.di.generic.instance
import java.lang.IndexOutOfBoundsException

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

    private val predefinedWallet = hashMapOf<Type, Wallet>(
        Type.AlipayBalance to Wallet("支付宝余额", WalletType.Real,predefined=true, icon=R.mipmap.zfb),
        Type.WechatBalance to Wallet("微信余额", WalletType.Real,predefined=true, icon=R.mipmap.wechat),
        Type.Huabei to Wallet("蚂蚁花呗", WalletType.Virtual, predefined=true, icon=R.mipmap.huabei)
    )

    fun initDb() {
        val databaseHelper: DatabaseHelper by App.getKodein().instance()
        val dao: Dao<Wallet, Int> = databaseHelper.getDao(Wallet::class.java)
        insertPredefinedToDb(dao)
    }

    private fun insertPredefinedToDb(dao: Dao<Wallet, Int>) {
        for (wa in predefinedWallet) {
            try {
                val w = dao.queryForEq(Wallet::name.name, wa.value.name)[0]
                if (w.icon != wa.value.icon) {
                    w.icon = wa.value.icon
                    dao.update(w)
                }
            } catch(e: IndexOutOfBoundsException) {
                dao.create(wa.value)
                Log.i("WalletFactory", "create predefined tag ${wa.value.name}")
            }
        }
    }

    fun getPredefined(type: Type): Wallet = predefinedWallet[type]!!
}