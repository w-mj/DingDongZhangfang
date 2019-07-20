package com.dingdonginc.zhangfang.models

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.dingdonginc.zhangfang.R
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import android.util.Log
import com.dingdonginc.zhangfang.App
import com.j256.ormlite.dao.Dao
import org.kodein.di.generic.instance
import java.sql.SQLException




const val DBName = "dingdongzhangfang.db"
const val DBVersion = 2

class DatabaseHelper constructor(context: Context) :
    OrmLiteSqliteOpenHelper(context, DBName, null, DBVersion, R.raw.ormlite_config) {


    /**
     * 获得单例的Helper，不是private
     */
    private var daos: HashMap<String, Dao<*, *>> = HashMap()
//    companion object {
//        private var instance: DatabaseHelper? = null
//
//
//        fun getHelper(context: Context?): DatabaseHelper {
//            if (instance == null) {
//                synchronized(DatabaseHelper::class.java) {
//                    if (instance == null) {
//                        context!!  // assert (context != null)
//                        val con = context.getApplicationContext()
//                        instance = DatabaseHelper(con)
//                    }
//                }
//            }
//            return instance as DatabaseHelper
//        }
//    }

//    companion object fun getHelper() = getHelper(null)

    /**
     * 更新数据库
     */
    override fun onUpgrade(
        database: SQLiteDatabase?,
        connectionSource: ConnectionSource?,
        oldVersion: Int,
        newVersion: Int
    ) {
        try {
            Log.i(DatabaseHelper::class.java.name, "onUpgrade")
            TableUtils.dropTable<Wallet, Int>(connectionSource, Wallet::class.java, true)
            TableUtils.dropTable<Tag, Int>(connectionSource, Tag::class.java, true)
            TableUtils.dropTable<Account, Int>(connectionSource, Account::class.java, true)
            onCreate(database, connectionSource)
        } catch (e: SQLException) {
            Log.e(DatabaseHelper::class.java.name, "Can't drop databases", e)
            throw RuntimeException(e)
        }
    }

    /**
     * 创建数据库
     */
    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        try {
            Log.i(DatabaseHelper::class.java.name, "onCreate")
            TableUtils.createTable(connectionSource, Wallet::class.java)
            TableUtils.createTable(connectionSource, Tag::class.java)
            TableUtils.createTable(connectionSource, Account::class.java)
            val tagFactory: TagFactory by App.getKodein().instance()
            tagFactory.insertPredefinedToDb(getDao(Tag::class.java))
        } catch (e: SQLException) {
            Log.e(DatabaseHelper::class.java.name, "Can't create database", e)
            throw RuntimeException(e)
        }
    }

    /**
     * 获取DAO?
     */
    @Synchronized
    @Throws(SQLException::class)
    override fun <D : Dao<T, *>, T> getDao(clazz: Class<T>?): D {
        val dao: Dao<*, *>?
        val className = clazz!!.simpleName

        if (daos.containsKey(className)) {
            dao = daos[className]
        } else {
            dao = super.getDao(clazz)
            daos[className] = dao
        }
        return dao as D
    }
}