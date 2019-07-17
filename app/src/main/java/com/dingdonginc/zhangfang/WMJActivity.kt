package com.dingdonginc.zhangfang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dingdonginc.zhangfang.models.WalletFactory

class WMJActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wmj)
        WalletFactory.alipayBalance(this)
    }
}
