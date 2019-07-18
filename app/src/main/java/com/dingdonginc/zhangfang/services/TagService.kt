package com.dingdonginc.zhangfang.services

import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.models.DatabaseHelper
import com.dingdonginc.zhangfang.models.Tag
import org.kodein.di.generic.instance

class TagService() {

    fun getAll(): List<Tag> {
        val helper: DatabaseHelper by App.getKodein().instance()
        return helper.getDao(Tag::class.java).queryForAll()
    }

    fun addTag(tag: Tag): Int {
        val helper: DatabaseHelper by App.getKodein().instance()
        return helper.getDao(Tag::class.java).create(tag);
    }
}