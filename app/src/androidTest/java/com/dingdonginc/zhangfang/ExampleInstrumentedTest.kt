package com.dingdonginc.zhangfang

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.dingdonginc.zhangfang.services.TestService

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.kodein.di.generic.instance

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.dingdonginc.zhangfang", appContext.packageName)
    }
}
@RunWith(AndroidJUnit4::class)
class DependencyInjectionTest {
    @Test
    fun diTest() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        val testService: TestService by App.getKodein().instance()
        assertEquals("com.dingdonginc.zhangfang", testService.packageName())
    }
}