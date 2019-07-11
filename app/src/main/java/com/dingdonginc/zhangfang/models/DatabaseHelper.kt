//package com.dingdonginc.zhangfang.models
//
//import android.content.Context
//import android.database.sqlite.SQLiteDatabase
//import com.dingdonginc.zhangfang.R
//import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
//import com.j256.ormlite.support.ConnectionSource
//
//class DatabaseHelper(context: Context) :
//    OrmLiteSqliteOpenHelper(context, "didazhangfang.db", null, 1, R.raw.ormlite_config) {
//    // name of the database file for your application -- change to something appropriate for your app
//    private val DATABASE_NAME = "didazhangfang.db"
//    // any time you make changes to your database objects, you may have to increase the database version
//    private val DATABASE_VERSION = 1
//
//    private val databaseHelper: DatabaseHelper? = null
//
//    override fun onUpgrade(
//        database: SQLiteDatabase?,
//        connectionSource: ConnectionSource?,
//        oldVersion: Int,
//        newVersion: Int
//    ) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//}