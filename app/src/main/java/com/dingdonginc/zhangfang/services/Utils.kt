package com.dingdonginc.zhangfang.services

import java.net.URLEncoder

object Utils {
    fun <K, V> map(iterator: Iterable<K>, transformer: (K)->V): List<V> {
        return iterator.map(transformer)
    }

    fun urlEncodeFormData(data: Map<String, String>): String {
        val d1 = data.map {"${URLEncoder.encode(it.key, "utf-8")}=${URLEncoder.encode(it.value, "utf-8")}" }
        return d1.joinToString("&")
    }
}