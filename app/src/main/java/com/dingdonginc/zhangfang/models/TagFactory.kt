package com.dingdonginc.zhangfang.models

import android.util.Log
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.R
import com.j256.ormlite.dao.Dao
import org.kodein.di.generic.instance
import java.lang.Exception


class TagFactory {
    private var idCache: HashMap<String, Int> = HashMap()
    enum class Type {
        Cloth, DailyUse, DailyCost, Eat, Pet, Shopping, Food
    }
    companion object {
        val predefinedTag: HashMap<Type, Tag> = hashMapOf(
            Type.Cloth to Tag("服装", R.mipmap.cloth),
            Type.DailyCost to Tag("日常消费", R.mipmap.dailyuse),
            Type.DailyUse to Tag("日用品", R.mipmap.dailycost),
            Type.Eat to Tag("餐饮", R.mipmap.eat),
            Type.Pet to Tag("宠物", R.mipmap.pet),
            Type.Shopping to Tag("购物", R.mipmap.shopping),
            Type.Food to Tag("食物", R.mipmap.vegetable)
        )
    }

    fun insertPredefinedToDb(dao: Dao<Tag, Int>) {
        for (pre in predefinedTag) {
            if (dao.queryForEq("name", pre.value.name).count() > 0) {
                continue
            }
            dao.create(pre.value)
            Log.i("TagFactory", "create predefined tag ${pre.value.name}")
        }
    }

    private var nameIcon: HashMap<String, Int> = hashMapOf(
        "服装" to R.mipmap.cloth,
        "日常消费" to R.mipmap.dailycost,
        "日用品" to R.mipmap.dailyuse,
        "食品" to R.mipmap.eat
    )

    private fun generateTag(name: String): Tag {
        val t = Tag()
        t.icon = R.mipmap.cloth
        t.name = name
        t.comment = ""
        return t
    }

    private fun getTag(name: String): Tag {
        Log.i("getTag", name)
        val helper: DatabaseHelper by App.getKodein().instance()
        val dao = helper.getDao(Tag::class.java)
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

    fun getPredefined(which: Type): Tag {
        return predefinedTag[which]!!
    }

    fun cloth() = getTag("服装")
}