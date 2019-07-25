package com.dingdonginc.zhangfang

import android.app.Application
import com.dingdonginc.zhangfang.models.DatabaseHelper
import com.dingdonginc.zhangfang.models.TagFactory
import com.dingdonginc.zhangfang.models.WalletFactory
import com.dingdonginc.zhangfang.services.*
import com.dingdonginc.zhangfang.services.MessageService
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

class App: Application(), KodeinAware {
    override val kodein: Kodein
        get() = Kodein.lazy {
            import(androidModule(this@App))
            bind<DatabaseHelper>() with singleton { DatabaseHelper(applicationContext) }
            bind<TagFactory>() with singleton { TagFactory() }
            bind<WalletFactory>() with singleton { WalletFactory() }
            bind<AccountService>() with singleton { AccountService() }
            bind<TagService>() with singleton { TagService() }
            bind<WalletService>() with singleton { WalletService() }
            bind<TestService>() with singleton { TestService(applicationContext) }
            bind<ExpressionService>() with singleton { ExpressionService() }
            bind<MainActivityDialogService>() with singleton { MainActivityDialogService() }
            bind<MessageService>() with singleton { MessageService() }
            bind<ActivityService>() with singleton { ActivityService() }
        }

    companion object {
        private lateinit var _kodein: Kodein
        fun getKodein(): Kodein {
            return _kodein
        }
    }
    override fun onCreate() {
        super.onCreate()
        App._kodein = kodein
    }
}