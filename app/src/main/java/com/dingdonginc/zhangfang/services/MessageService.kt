package com.dingdonginc.zhangfang.services

import android.os.Handler
import android.os.Message
import java.lang.Exception

class MessageService {
    private val registered = HashMap<String, Handler>()

    fun <T: Handler.Callback> register(obj: T) {
        registered[obj::class.java.simpleName] = Handler(obj)
    }

    fun <T: Handler.Callback> sendMessage(msg: Message, to: Class<T>) {
        if (to.simpleName !in registered) {
            throw Exception("Unregistered message receiver ${to.simpleName}")
        }
        registered[to.simpleName]?.sendMessage(msg)
    }

    fun <T: Handler.Callback> getHandler(to: Class<T>): Handler {
        return registered[to.simpleName]!!
    }
}
