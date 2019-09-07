package com.dingdonginc.zhangfang.viewmodels

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.lib.command.RelayCommand
import com.dingdonginc.zhangfang.models.Account
import com.dingdonginc.zhangfang.models.Tag
import com.dingdonginc.zhangfang.models.TagFactory
import com.dingdonginc.zhangfang.models.Wallet
import com.dingdonginc.zhangfang.services.*
import com.dingdonginc.zhangfang.services.converter.Converter
import com.dingdonginc.zhangfang.views.MainActivity
import org.kodein.di.generic.instance
import rx.functions.Action0
import rx.functions.Action1
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddAccountViewModel : ViewModel() {
    /* * * binding data * * */
    val currentInput = ObservableField<String>()
    val datetime = ObservableField<String>()
    var typeList = ArrayList<ArrayList<Tag>>()
    var currentTag = ObservableField<Tag>()
    val selectedWallet = ObservableField(0)
    var walletIcon: List<Int>
    var mode = "CalendarTime"

    /* * * private variables * * */
    private val parser = SimpleDateFormat("yyyy-MM-dd\nHH:mm", Locale.CHINA)
    private var tagList: List<Tag>
    private var method = "1"
    private var selectableWallet: List<Wallet>

    /* * * private services * * */
    private val tagService: TagService by App.getKodein().instance()
    private val activityService: ActivityService by App.getKodein().instance()
    private val walletService: WalletService by App.getKodein().instance()
    private val tagFactory: TagFactory by App.getKodein().instance()

    init {
        //init current input
        currentInput.set("")
        //init current date
        val now = Date()
        datetime.set(parser.format(now))
        //init current tag
        val temptag = tagFactory.getPredefined(TagFactory.Type.Unknown)
        currentTag.set(temptag)
        //init all tags
        tagList = tagService.getAll()
        typeList = Converter.GroupTagList(tagList)
        //init all wallets
        selectableWallet = walletService.getAll()
        walletIcon = selectableWallet.map { it.icon }
    }

    /* * * binding commands * * */
    /**
     * @summary Selecte a tag when you create an account artificially, this command is bound
     *           LinearLayout's linearClickCommand and it will use LinearLayout'tag to change the
     *           current tag
     * @param tagId the LinearLayout's tag
     */
    val selectTag = RelayCommand<Int>(Action1<Int> { tagId->
        run {
            val tag = tagList.find { it.id == tagId }
            currentTag.set(tag)
        }
    })

    val selectDate = RelayCommand<String>(Action1 { content->
        run{
            datetime.set(content)
        }
    })

    /**
     * @summary This command is bound on each digit key to show the content of keybroad
     * @param content the text of botton
     */
    val digitalKeyClick = RelayCommand<String>(Action1<String> keyend@{ content->
        run{
            Log.d(content, content)
            val str = currentInput.get()?: ""
            if (str == "") {
                // 输入序列为空
                if (content == "+" || content == "-")  // 忽略第一个+-
                    return@keyend
                if (content == ".")  // 第一个.前面加0
                    currentInput.set("0.")
                else
                    currentInput.set(content)  // 数字键
            } else {
                if (content == "+" || content == "-") {
                    if (str.last() == '+' || str.last() == '-')  // 忽略连续的符号
                        return@keyend
                    if (str.last() == '.')  // .后面直接接符号，去掉.
                        currentInput.set(str.substring(0, str.lastIndex) + content)
                    else
                        currentInput.set(str + content)  // 加入符号
                } else if (content == ".") {
                    var i = str.lastIndex
                    while (i >= 0) {
                        if (str[i] == '+' || str[i] == '-')
                            break
                        if (str[i] == '.')
                            return@keyend  // 同一个数字里面不能有两个点
                        i--
                    }
                    if (i == str.lastIndex)  // 点之前是符号，加个0
                        currentInput.set("${str}0.")
                    else
                        currentInput.set("$str.")
                } else {
                    if (str.length > 3 && str[str.length - 3] == '.')  // 小数位数不能超过3
                        return@keyend
                    currentInput.set(str + content)  // 加入数字
                }
            }
        }
    })

    /**
     * @summary Delete the ending character of currentInput
     * @param no param
     */
    val backSpace = RelayCommand<Nothing>(Action0 backend@{
        val str = currentInput.get()?:""
        if (str == "")
            return@backend
        currentInput.set(str.substring(0, str.length - 1))
    })

    /**
     * @summary Create an bill artificially based on the current page's infomation
     * @param no param
     */
    val submit = RelayCommand<Nothing>(Action0 submitend@{
        val expSer: ExpressionService by App.getKodein().instance()
        val str = currentInput.get()?:return@submitend
        val datetime = parser.parse(datetime.get()!!)!!
        if (selectedWallet.get() == null)
            return@submitend
        val amount = expSer.eval(str)
        if (amount != 0) {
            val accountService: AccountService by App.getKodein().instance()
            val acc = Account(
                wallet = selectableWallet[selectedWallet.get()!!],
                amount = amount,
                tag = currentTag.get()!!,
                time = datetime
            )
            accountService.addAccount(acc)
            val notificationService: NotificationService by App.getKodein().instance()
            notificationService.sendAutoTally(amount)
        }
        activityService.switchActivity(MainActivity::class.java)
    })
}
