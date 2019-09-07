package com.dingdonginc.zhangfang.models

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.dingdonginc.zhangfang.App
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.kodein.di.generic.instance


@RunWith(AndroidJUnit4::class)
class DatabaseHelperTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.dingdonginc.zhangfang", appContext.packageName)
    }

    @Test
    fun testSingleton(){
        // 测试单例
        val appContext = InstrumentationRegistry.getTargetContext()

        val helper1 : DatabaseHelper by App.getKodein().instance()
        val helper2 : DatabaseHelper by App.getKodein().instance()
        assertEquals(System.identityHashCode(helper1), System.identityHashCode(helper2))
    }

    @Test
    fun testDataBase() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val helper :DatabaseHelper by App.getKodein().instance()
        val method_dao = helper.getDao(Wallet::class.java)
        method_dao.delete(method_dao.queryForAll())

        val m = Wallet()
        m.comment = "方法注释"
        m.name = "测试方法"
        m.type = WalletType.Real
        m.predefined = true
        m.hidden = false
        method_dao.create(m)
        assertEquals(method_dao.count(), 1)
        assertEquals(method_dao.queryForAll()[0].name, "测试方法")
    }

    @Test
    fun testWalletFactory() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val helper :DatabaseHelper by App.getKodein().instance()
        val method_dao = helper.getDao(Wallet::class.java)
        method_dao.delete(method_dao.queryForAll())
        val walletFactory: WalletFactory by App.getKodein().instance()
        var m = walletFactory.getPredefined(WalletFactory.Type.AlipayBalance)
        assertEquals(m.name, "支付宝余额")
        m = walletFactory.getPredefined(WalletFactory.Type.AlipayBalance)
        assertEquals(m.name, "支付宝余额")
        m = walletFactory.getPredefined(WalletFactory.Type.AlipayBalance)
        assertEquals(m.name, "支付宝余额")
        assertEquals(method_dao.count(), 0)
    }
}