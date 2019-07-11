package com.dingdonginc.zhangfang.models

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

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
        val helper1 = DatabaseHelper.getHelper(appContext)
        val helper2 = DatabaseHelper.getHelper(appContext)
        assertEquals(System.identityHashCode(helper1), System.identityHashCode(helper2))
    }

    @Test
    fun testDataBase() {
        val appContext = InstrumentationRegistry.getTargetContext()
        var helper = DatabaseHelper.getHelper(appContext)
        var method_dao = helper.getDao(Method::class.java)
        method_dao.delete(method_dao.queryForAll())

        val m = Method()
        m.comment = "方法注释"
        m.name = "测试方法"
        m.type = MethodType.Real
        m.predefined = true
        m.hidden = false
        method_dao.create(m)
        assertEquals(method_dao.count(), 1)
        assertEquals(method_dao.queryForAll()[0].name, "测试方法")
    }
}