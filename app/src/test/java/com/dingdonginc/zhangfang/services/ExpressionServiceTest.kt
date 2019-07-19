package com.dingdonginc.zhangfang.services

import org.junit.Test

import org.junit.Assert.*

class ExpressionServiceTest {

    @Test
    fun eval() {
        val evalser = ExpressionService()
        assertEquals(evalser.toMoney("12"), 1200)
        assertEquals(evalser.toMoney("12.3"), 1230)

        var sum = evalser.eval("10+56.2-9+6.78-88+14.3+7+5+6-0-0")
        assertEquals(sum, 1000+5620-900+678-8800+1430+700+500+600)
        sum = evalser.eval("10+56.2-9+6.78-88+14.3+7+5+6")
        assertEquals(sum, 1000+5620-900+678-8800+1430+700+500+600)
        sum = evalser.eval("1")
        assertEquals(sum, 100)
        sum = evalser.eval("0")
        assertEquals(sum, 0)
    }
}