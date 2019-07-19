package com.dingdonginc.zhangfang

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.view.View
import android.widget.AdapterView
import com.dingdonginc.zhangfang.models.Check
import org.json.JSONObject
import java.net.URL

import kotlinx.android.synthetic.main.content_main.*

import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.dingdonginc.zhangfang.layoutservice.ContentMainAdapter
import com.dingdonginc.zhangfang.layoutservice.DayAccountAdapter
import com.dingdonginc.zhangfang.layoutservice.ViewPagerAdapter
import com.dingdonginc.zhangfang.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    private var viewPager : ViewPager ?= null
    private var bnv : BottomNavigationView ?= null
    //ViewPagerListener override function
    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        val menuItem = bnv?.getMenu()?.getItem(position)
        menuItem?.setChecked(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: com.dingdonginc.zhangfang.databinding.ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)

        var vm = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewPager = findViewById(R.id.viewpager)
        bnv = findViewById(R.id.bottom_nav_view)

        bnv?.setOnNavigationItemSelectedListener(this)
        var lst = ArrayList<MainViewModel>()
        lst.add(vm)
        lst.add(vm)
        lst.add(vm)
        var adapter = ViewPagerAdapter<MainViewModel>(this, lst, BR.mainViewModel, R.layout.app_bar_main, getLayoutInflater())
        viewPager?.setAdapter(adapter)
        viewPager?.setPageTransformer(true, ViewPagerTransformer())
        viewPager?.setOnPageChangeListener(this)

        binding?.let {
            it.mainViewModel = vm
            it.setLifecycleOwner(this)
        }
//        var lv : ListView = findViewById(R.id.ma)
//        lv.adapter = ca

//        Thread(Runnable {
//            var readText:String = URL("http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=10&page=1").readText()
//            val jsonObject = JSONObject(readText)
//            val jsonArray = jsonObject.getJSONArray("data")
//            for(i in 0..jsonArray.length()-1 step 1){
//                val jsonObject1 = jsonArray.getJSONObject(i)
//                val title = jsonObject1.getString("title")
//                list?.add(Check(title))
//            }
//        }).start()
////        Thread.sleep(1000)
//        list?.add(Check("123"))
//        lv?.adapter=ListViewAdapter(this,list!!)

        //通知权限是否获得
        if (!isNotificationListenerEnabled(this)) {
            openNotificationListenSettings()
        }
        //KtoggleNotificationListenerService();


//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)

//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        //val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        //val navView: NavigationView = findViewById(R.id.nav_view)
//        val toggle = ActionBarDrawerToggle(
//            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
//        )
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()

        //navView.setNavigationItemSelectedListener(this)


    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar listview_item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view listview_item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                viewpager.setCurrentItem(0)
            }
            R.id.nav_chart -> {
                viewpager.setCurrentItem(1)
            }
            R.id.nav_fund -> {
                viewpager.setCurrentItem(2)
            }
        }
//        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
//        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    //检测通知监听是否被授权
    fun isNotificationListenerEnabled(context: Context): Boolean {
        val packageNames = NotificationManagerCompat.getEnabledListenerPackages(this)
        if (packageNames.contains(context.packageName)) {
            Log.i("Private", "successful")
            return true
        }
        return false
    }

    //打开通知监听设置页面
    fun openNotificationListenSettings() {
        try {
            val intent: Intent
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            } else {
                intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            }
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private class ViewPagerTransformer : ViewPager.PageTransformer{
        override fun transformPage(page: View, position: Float) {
            if (position < -1) {
                page.setAlpha(0F);
            } else if (position <= 0) {
                // 左右移动，并且移除时变透明
                page.setAlpha(1 + position);
            } else if (position < 1) {
                // 去除左右移动效果
                page.setTranslationX(-page.getWidth() * position);
                // 进入时变大，移除时变小
                page.setScaleX(1 - position/2);
                page.setScaleY(1 - position/2);
                page.setAlpha(1 - position);
            } else {
                page.setAlpha(0F);
            }
        }

    }
}

