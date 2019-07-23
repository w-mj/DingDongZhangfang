package com.dingdonginc.zhangfang

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.dingdonginc.zhangfang.services.AccountService
import com.dingdonginc.zhangfang.viewmodels.MainViewModel
import com.dingdonginc.zhangfang.views.AddAccountFragment
import com.test.chart.PieChartFragment
import org.kodein.di.generic.instance

class WMJActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wmj)
        val data: ArrayList<String> = ArrayList()
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
        val list: ListView = findViewById<ListView>(R.id.wmj_list)
        list.adapter = adapter
        val context: Context = this
        val refresh: Button = findViewById(R.id.wmj_refresh)

        val accountService: AccountService by App.getKodein().instance()

        refresh.setOnClickListener { v: View -> run{
            val accountList = accountService.getAll()
            Log.i("account num", accountList.size.toString())
            data.clear()
            for (acc in accountList) {
                data.add(acc.toString())
            }
            adapter.notifyDataSetChanged()
        }}

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        val fr = PieChartFragment()
        ft.add(R.id.wmj_frame, fr)
        ft.show(fr)
        ft.commitAllowingStateLoss()
    }
}
