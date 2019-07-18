package com.dingdonginc.zhangfang

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import com.dingdonginc.zhangfang.services.AccountService
import java.util.*
import kotlin.collections.ArrayList

class WMJActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wmj)
        val data: ArrayList<String> = ArrayList()
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        val list: ListView = findViewById(R.id.wmj_list)
        list.adapter = adapter
        val context: Context = this
        val refresh: Button = findViewById(R.id.wmj_refresh)
        refresh.setOnClickListener { v: View -> run{
            val accountList = AccountService.getAll(context)
            Log.i("account num", accountList.size.toString())
            data.clear()
            for (acc in accountList) {
                data.add(acc.toString())
            }
            adapter.notifyDataSetChanged()
        }}
    }
}
