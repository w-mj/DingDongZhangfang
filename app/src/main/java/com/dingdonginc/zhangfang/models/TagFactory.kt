package com.dingdonginc.zhangfang.models

import android.content.Context
import android.util.Log
import java.lang.Exception

object TagFactory {
    private var idCache: HashMap<String, Int> = HashMap()

    private fun generateTag(name: String): Tag {
        val t = Tag()
        t.icon = 0
        t.name = name
        t.comment = ""
        return t
    }

    private fun getTag(name: String , context: Context): Tag {
        Log.i("getTag", name)
        val dao = DatabaseHelper.getHelper(context).getDao(Tag::class.java)
        val query = dao.queryBuilder()
        if (!idCache.containsKey(name)) {
            query.where().eq("name", name)
        } else {
            query.where().eq("id", idCache[name])
        }
        val queryResult = query.query()
        when {
            queryResult.size == 0 -> {
                Log.i("getTag", "Build $name balance")
                val w = generateTag(name)
                dao.create(w)
                idCache[name] = w.id
                return w
            }
            queryResult.size == 1 -> {
                return queryResult[0]
            }
            else -> {
                Log.e("getTag", "query $name more than 1")
                throw Exception()
            }
        }
    }

    fun cloth(context: Context) = getTag("服装", context)
}