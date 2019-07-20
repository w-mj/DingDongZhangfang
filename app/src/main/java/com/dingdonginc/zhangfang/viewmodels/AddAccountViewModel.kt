package com.dingdonginc.zhangfang.viewmodels

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.databinding.BindingConversion
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel;
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.models.Tag
import com.dingdonginc.zhangfang.services.ExpressionService
import org.kodein.di.generic.instance
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddAccountViewModel : ViewModel() {
    val currentInput = ObservableField<String>()
    val datetime = ObservableField<String>()
    val sum = 0
    val typeList = ArrayList<ArrayList<Tag>>()
    var currentTag = ObservableField<Tag>()
    private val parser = SimpleDateFormat("yyyy-MM-dd\nHH:mm", Locale.CHINA)

    init {
        currentInput.set("")
        val now = Date()
        datetime.set(parser.format(now))
        var temptag = Tag()
        temptag.comment = "其他"
        currentTag.set(temptag)
        var list = ArrayList<Tag>()
        var list1 = ArrayList<Tag>()
        var ta = Tag()
        ta.comment = "111"
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
        typeList.add(list)
        typeList.add(list1)
    }

    fun selectType(view: View){
        var linearLayout = view as LinearLayout
        var tag = Tag()
        tag.comment = linearLayout.tag.toString()
        currentTag.set(tag)
    }

    fun onDigitalKeyClick(view: View) {
        val b: Button = view as Button
        val str = currentInput.get()?: ""
        if (str == "") {
            // 输入序列为空
            if (b.text == "+" || b.text == "-")  // 忽略第一个+-
                return
            if (b.text == ".")  // 第一个.前面加0
                currentInput.set("0.")
            else
                currentInput.set(b.text.toString())  // 数字键
        } else {
            if (b.text == "+" || b.text == "-") {
                if (str.last() == '+' || str.last() == '-')  // 忽略连续的符号
                    return
                if (str.last() == '.')  // .后面直接接符号，去掉.
                    currentInput.set(str.substring(0, str.lastIndex) + b.text)
                else
                    currentInput.set(str + b.text)  // 加入符号
            } else if (b.text == ".") {
                var i = str.lastIndex
                while (i >= 0) {
                    if (str[i] == '+' || str[i] == '-')
                        break
                    if (str[i] == '.')
                        return  // 同一个数字里面不能有两个点
                    i--
                }
                if (i == str.lastIndex)  // 点之前是符号，加个0
                    currentInput.set("${str}0.")
                else
                    currentInput.set("$str.")
            } else {
                if (str.length > 3 && str[str.length - 3] == '.')  // 小数位数不能超过3
                    return
                currentInput.set(str + b.text.toString())  // 加入数字
            }
        }
    }

//    val submitCommand: ReplyCommand

    fun onBackspaceClick(view: View) {
        val str = currentInput.get()?:""
        if (str == "")
            return
        currentInput.set(str.substring(0, str.length - 1))
    }

    fun onSubmitClick(view: View) {
        val expSer: ExpressionService by App.getKodein().instance()
        val str = currentInput.get()?:return
        val datetime = parser.parse(datetime.get()!!)!!
        Log.i("创建账目", expSer.eval(str).toString() + " at ${datetime.toString()}")
    }
}
