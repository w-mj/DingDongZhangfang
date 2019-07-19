package com.dingdonginc.zhangfang.services

class ExpressionService {

    fun toMoney(exp: String): Int {
        var dot = false;
        var sum = 0
        var dots = 0
        for (c in exp) {
            assert(c in '0'..'9' || c == '.')
            if (c == '.')
                dot = true
            else {
                sum = sum * 10 + (c - '0')
                if (dot)
                    dots++
            }
        }
        assert(dots <= 2)
        if (dots == 0)
            sum *= 100
        if (dots == 1)
            sum *= 10
        return sum
    }

    fun eval(exp: String): Int {
        var sum = 0
        var op = '+'
        var i = 0
        var s = i
        while (i < exp.length) {
            if (exp[i] == '+' || exp[i] == '-') {
                if (op == '+')
                    sum += toMoney(exp.substring(s, i))
                else
                    sum -= toMoney(exp.substring(s, i))
                op = exp[i]
                s = i + 1
            }
            i++
        }
        if (op == '+')
            sum += toMoney(exp.substring(s, i))
        else
            sum -= toMoney(exp.substring(s, i))
        return sum
    }
}