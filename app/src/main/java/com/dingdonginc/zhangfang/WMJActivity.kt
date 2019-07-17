package com.dingdonginc.zhangfang

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dingdonginc.zhangfang.models.Wallet

class WMJActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wmj)
        Log.i("Reflection", Wallet::balance.name)
    }
}
