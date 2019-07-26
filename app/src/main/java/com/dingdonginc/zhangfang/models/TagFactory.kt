package com.dingdonginc.zhangfang.models

import android.util.Log
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.R
import com.j256.ormlite.dao.Dao
import org.kodein.di.generic.instance
import java.lang.Exception


class TagFactory {
    init {
        Log.d("TagFactory", "init")
    }
    private var idCache: HashMap<String, Int> = HashMap()
    enum class Type {
        Cloth, DailyUse, DailyCost, Eat,
        Pet, Shopping, Food,
        Study, Transport, Camera, Shower,
        Tour, Cigarette, Fruit, House, Makeup, Health, Party, Unknown
    }
    val predefinedTag: HashMap<Type, Tag> = hashMapOf(
        Type.Cloth to Tag("服装", R.mipmap.cloth),
        Type.DailyCost to Tag("水电支出", R.mipmap.dailycost),
        Type.DailyUse to Tag("日用品", R.mipmap.dailyuse),
        Type.Eat to Tag("餐饮", R.mipmap.eat),
        Type.Pet to Tag("宠物", R.mipmap.pet),
        Type.Shopping to Tag("购物", R.mipmap.shopping),
        Type.Food to Tag("食物", R.mipmap.vegetable),
        Type.Study to Tag("学习", R.mipmap.book),
        Type.Camera to Tag("摄影", R.mipmap.camera),
        Type.Transport to Tag("交通", R.mipmap.transport),
        Type.Tour to Tag("旅行", R.mipmap.tour),
        Type.Cigarette to Tag("吸烟？", R.mipmap.cigarette),
        Type.Fruit to Tag("水果？", R.mipmap.fruit),
        Type.House to Tag("居住", R.mipmap.house),
        Type.Makeup to Tag("美容", R.mipmap.makeup),
        Type.Health to Tag("健康", R.mipmap.medicine),
        Type.Party to Tag("聚会", R.mipmap.party),
        Type.Shower to Tag("沐浴", R.drawable.shower),
        Type.Unknown to Tag("未指定", R.drawable.baseline_sentiment_very_satisfied_black_36)
    )

    fun initDb() {
        val databaseHelper: DatabaseHelper by App.getKodein().instance()
        val dao: Dao<Tag, Int> = databaseHelper.getDao(Tag::class.java)
        insertPredefinedToDb(dao)
    }

    private fun insertPredefinedToDb(dao: Dao<Tag, Int>) {
        for (pre in predefinedTag) {
            try {
                val t = dao.queryForEq(Tag::name.name, pre.value.name)[0]
                if (t.icon != pre.value.icon) {
                    t.icon = pre.value.icon
                    dao.update(t)
                    Log.i("TagFactory", "update predefined tag ${pre.value.name} ${pre.value.id}")
                }
                pre.value.id = t.id
            } catch(e: IndexOutOfBoundsException) {
                dao.create(pre.value)
                Log.i("TagFactory", "create predefined tag ${pre.value.name} ${pre.value.id}")
            }
            dao.refresh(pre.value)
        }
    }

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

    fun getPredefined(which: Type): Tag = predefinedTag[which]!!

    fun cloth() = getTag("服装")
}