package com.dingdonginc.zhangfang.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.R
import com.dingdonginc.zhangfang.models.TagFactory
import com.dingdonginc.zhangfang.models.WalletFactory
import com.dingdonginc.zhangfang.services.ActivityService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.chart.PieChartFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MainActivity :
        AppCompatActivity(),
        BottomNavigationView.OnNavigationItemSelectedListener,
        ViewPager.OnPageChangeListener,
        KodeinAware
{
    private val _parentKodein by closestKodein()
    override val kodein: Kodein = Kodein.lazy {
        extend(_parentKodein)
        bind<AccountListFragment>() with singleton { AccountListFragment() }
        bind<WalletFragment>() with singleton { WalletFragment() }
        bind<ChartFragment>() with singleton { ChartFragment() }
    }

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

    private fun hideAllFragment() {
        val ft = supportFragmentManager.beginTransaction()
        for (f in supportFragmentManager.fragments)
            ft.hide(f)
        ft.commitAllowingStateLoss()
    }

    private inline fun <reified T : Fragment> showFragment() {
        hideAllFragment()
        val ft = supportFragmentManager.beginTransaction()
        val f: T by kodein.instance<T>()
        if (f !in supportFragmentManager.fragments) {
            ft.add(R.id.main_frame, f)
        }
        ft.show(f)
        ft.commitAllowingStateLoss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme_NoActionBar)
        setContentView(R.layout.activity_main)

        val tagFactory: TagFactory by kodein.instance()
        tagFactory.initDb()
        val walletFactory: WalletFactory by kodein.instance()
        walletFactory.initDb()

        val activityService: ActivityService by App.getKodein().instance()
        activityService.activity = this

//        val binding: com.dingdonginc.zhangfang.databinding.ActivityMainBinding =
//            DataBindingUtil.setContentView(this, R.layout.activity_main)
//
//        val vm = ViewModelProviders.of(this).get(AccountListViewModel::class.java)
        bnv = findViewById(R.id.bottom_nav_view)

        bnv?.setOnNavigationItemSelectedListener(this)

//        var lst = ArrayList<AccountListViewModel>()
//        lst.add(vm)
//        lst.add(vm)
//        lst.add(vm)
//        viewPager = findViewById(R.id.viewpager)
//
//        var adapter = ViewPagerAdapter<AccountListViewModel>(this, lst, BR.mainViewModel, R.layout.app_bar_main, getLayoutInflater())
//        viewPager?.setAdapter(adapter)
//        viewPager?.setPageTransformer(true, ViewPagerTransformer())
//        viewPager?.setOnPageChangeListener(this)

//        binding.let {
//            it.mainViewModel = vm
//            it.setLifecycleOwner(this)
//        }


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
            Log.i("MainActivity", "未获得读取通知权限")
            // openNotificationListenSettings()
        }
        //KtoggleNotificationListenerService();


//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)

//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener {
//            showFragment<AddAccountFragment>()
//        }
        //val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        //val navView: NavigationView = findViewById(R.id.nav_view)
//        val toggle = ActionBarDrawerToggle(
//            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
//        )
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()

        //navView.setNavigationItemSelectedListener(this)
        showFragment<AccountListFragment>()

    }

//    override fun onBackPressed() {
//        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
//        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START)
//        } else {
//            super.onBackPressed()
//        }
//    }

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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {1
        // Handle navigation view listview_item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                showFragment<AccountListFragment>()
//                viewpager.setCurrentItem(0)
            }
            R.id.nav_chart -> {
                showFragment<ChartFragment>()
//                viewpager.setCurrentItem(1)
            }
            R.id.nav_fund -> {
//                viewpager.setCurrentItem(2)
                showFragment<WalletFragment>()
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

