package com.dingdonginc.zhangfang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.dingdonginc.zhangfang.layoutservice.DayAccountAdapter
import com.dingdonginc.zhangfang.layoutservice.TypesAdapter
import com.dingdonginc.zhangfang.layoutservice.ViewPagerAdapter
import com.dingdonginc.zhangfang.models.Account
import com.dingdonginc.zhangfang.models.Check
import com.dingdonginc.zhangfang.models.Tag
import kotlinx.android.synthetic.main.uppart_editpage.*

class ZJHActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.uppart_editpage)
        var viewPager : ViewPager = findViewById(R.id.typeview)
        var lists = ArrayList<ArrayList<Tag>>()
        var list = ArrayList<Tag>()
        var list1 = ArrayList<Tag>()
        var ta = Tag()
        ta.comment = "111"
        list.add(ta)
        list.add(ta)
        list.add(ta)
        list.add(ta)
        list.add(ta)
        list.add(ta)
        list.add(ta)
        list.add(ta)
        var ta1 = Tag()
        ta1.comment = "222"
        list1.add(ta1)
        list1.add(ta1)
        list1.add(ta1)
        list1.add(ta1)
        list1.add(ta1)
        list1.add(ta1)
        list1.add(ta1)
        list1.add(ta1)
        lists.add(list)
        lists.add(list1)
        //var adapter = ViewPagerAdapter<Tag>(lists, BR.tag , R.layout.typelist_item, getLayoutInflater())
        //viewPager?.setAdapter(adapter)
    }

}
