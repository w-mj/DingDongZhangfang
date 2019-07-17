package com.dingdonginc.zhangfang.services

import android.content.Context
import com.dingdonginc.zhangfang.models.DatabaseHelper
import com.dingdonginc.zhangfang.models.Tag

object TagService {

    fun getAll(context: Context?): List<Tag>
            = DatabaseHelper.getHelper(context).getDao(Tag::class.java).queryForAll()

    fun addTag(context: Context?, tag: Tag): Int
        = DatabaseHelper.getHelper(context).getDao(Tag::class.java).create(tag)

}