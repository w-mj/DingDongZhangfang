package com.dingdonginc.zhangfang.services

object Utils {
    fun <K, V> map(iterator: Iterable<K>, transformer: (K)->V): List<V> {
        return iterator.map(transformer)
    }
}