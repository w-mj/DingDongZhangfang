package com.dingdonginc.zhangfang

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CalculatorTest{
    private lateinit var mCalculator:Calculate
    @Before
    fun setUp(){
        mCalculator= Calculate()
    }

    @Test
    fun testAdd(){
        assertEquals(6,mCalculator.add(2,2))
    }

    @Test
    fun testMul(){
        assertEquals(40,mCalculator.mul(20,2))
    }
}