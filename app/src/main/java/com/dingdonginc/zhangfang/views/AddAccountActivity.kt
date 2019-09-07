package com.dingdonginc.zhangfang.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.services.ActivityService
import com.dingdonginc.zhangfang.services.MainActivityDialogService
import com.dingdonginc.zhangfang.services.neuspider.main
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class AddAccountActivity : AppCompatActivity(), KodeinAware {
    private val _kodein by closestKodein()
    override val kodein = Kodein.lazy {
        extend(_kodein)
        bind<AddAccountFragment>() with singleton {AddAccountFragment()}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_account_activity)
        val fragment: AddAccountFragment by kodein.instance()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()
        }
        val activityService: ActivityService by kodein.instance()
        activityService.activity = this
        val mainActivityDialogService: MainActivityDialogService by App.getKodein().instance()
        mainActivityDialogService.setFm(supportFragmentManager!!)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val activityService: ActivityService by App.getKodein().instance()
        activityService.switchActivity(MainActivity::class.java)
    }

}
