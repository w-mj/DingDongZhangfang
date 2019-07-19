package com.dingdonginc.zhangfang

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dingdonginc.zhangfang.layoutservice.DayAccountAdapter
import com.dingdonginc.zhangfang.layoutservice.TypesAdapter
import com.dingdonginc.zhangfang.models.Account
import com.dingdonginc.zhangfang.models.Check
import kotlinx.android.synthetic.main.uppart_editpage.*

class ZJHActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: com.dingdonginc.zhangfang.databinding.UppartEditpageBinding = DataBindingUtil.setContentView(
            this, R.layout.uppart_editpage)
        var recyclerView : RecyclerView = findViewById(R.id.rv1)
        var recyclerView1 : RecyclerView = findViewById(R.id.rv1)
        var layoutManager : LinearLayoutManager = LinearLayoutManager(this)
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL)
        recyclerView.setLayoutManager(layoutManager)
        recyclerView1.setLayoutManager(layoutManager)
        var tag = Account()
        tag.comment = "111"
        var tagList = ArrayList<Account>()
        tagList.add(tag)
        tagList.add(tag)
        tagList.add(tag)
        var ad = TypesAdapter(BR.tag, tagList)
        binding.setUpadapter(ad)
        binding.setDownadapter(ad)
    }
}
