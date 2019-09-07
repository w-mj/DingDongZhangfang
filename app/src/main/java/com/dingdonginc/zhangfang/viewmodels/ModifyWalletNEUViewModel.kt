package com.dingdonginc.zhangfang.viewmodels

import android.graphics.Bitmap
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.dingdonginc.zhangfang.App
import com.dingdonginc.zhangfang.models.Account
import com.dingdonginc.zhangfang.models.TagFactory
import com.dingdonginc.zhangfang.models.WalletFactory
import com.dingdonginc.zhangfang.services.AccountService
import com.dingdonginc.zhangfang.services.DialogDismissService
import com.dingdonginc.zhangfang.services.ToastService
import com.dingdonginc.zhangfang.services.neuspider.GetChapter
import com.dingdonginc.zhangfang.services.neuspider.NEUSpider
import com.dingdonginc.zhangfang.services.neuspider.NEUWallet
import com.dingdonginc.zhangfang.views.ModifyWalletNEUDialog
import org.kodein.di.generic.instance
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

class ModifyWalletNEUViewModel : ViewModel() {
    val loadingCaptcha = ObservableField<Boolean>(true)
    val username = ObservableField<String>("")
    val password = ObservableField<String>("")
    val captcha = ObservableField<String>("")
    val startDate = ObservableField<String>()
    val endDate = ObservableField<String>()
    val captchaImage = ObservableField<Bitmap>()

    init {
        refreshCaptcha()
        val dateParser = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        val now = Calendar.getInstance(Locale.CHINA)
        endDate.set(dateParser.format(now.time))
        now.add(Calendar.WEEK_OF_YEAR, -1)
        startDate.set(dateParser.format(now.time))

    }

    fun refreshCaptcha() {
        val refresh = RefreshCaptcha(WeakReference(loadingCaptcha), WeakReference(captchaImage))
        refresh.execute()
    }

    private class RefreshCaptcha(
        private val loading: WeakReference<ObservableField<Boolean>>,
        private val image: WeakReference<ObservableField<Bitmap>>
    ) : AsyncTask<Unit, Unit, Bitmap>() {
        override fun onPreExecute() {
            loading.get()?.set(true)
        }

        override fun doInBackground(vararg p0: Unit?): Bitmap? {
            val chapter = GetChapter()
            return chapter.get()
        }

        override fun onPostExecute(result: Bitmap) {
            super.onPostExecute(result)
            image.get()?.set(result)
            loading.get()?.set(false)
        }
    }

    fun submit(view: View?) {
        Log.d("NEUVM", "${username.get()}  ${password.get()}  ${captcha.get()}")
        GetNEUCard(
            username.get()!!,
            password.get()!!,
            captcha.get()!!,
            startDate.get()!!,
            endDate.get()!!
        ).execute()
    }

    private class GetNEUCard(
        val uid: String,
        val psd: String,
        val captcha: String,
        val startDate: String,
        val endDate: String
    ) :
        AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg p0: Unit?) {
            val neuSpider = NEUSpider()
            neuSpider.login(uid, psd, captcha)
            try {
                val list = neuSpider.get(startDate, endDate)
                list.forEach {
                    Log.d("NEUWALLET", it.toString())
                }

                val tagFactory: TagFactory by App.getKodein().instance()

                val walletFactory: WalletFactory by App.getKodein().instance()
                val accountService: AccountService by App.getKodein().instance()
                val parser = SimpleDateFormat("yyyy/M/d hh:mm:ss", Locale.CHINA)
                val wallet = walletFactory.getPredefined(WalletFactory.Type.NEUCard)
                val unknownTag = tagFactory.getPredefined(TagFactory.Type.Unknown)
                assert(wallet.id != 0)
                assert(unknownTag.id != 0)
                Log.d("wallet id", wallet.id.toString())
                Log.d("unknown id", unknownTag.id.toString())
                val accountList = list.map {
                    Account(
                        wallet = wallet,
                        amount = -(it.amount.toFloat() * 100).toInt(),
                        tag = it.getTag(),
                        time = parser.parse(it.time)!!,
                        partner = it.terminal,
                        comment = "操作员：${it.operator} 工作站：${it.station}",
                        generatedId = "NEU-${it.time}-${it.amount}"
                    ) }
                accountList.forEach { accountService.addAccount(it) }
                val dialogDismissService: DialogDismissService by App.getKodein().instance()
                dialogDismissService.dismiss(ModifyWalletNEUDialog::class.java)
            } catch (e: Exception) {
//                val toast :ToastService by App.getKodein().instance()
//                toast.makeText("获取账单失败", Toast.LENGTH_SHORT)
                return
            }
        }
    }

    fun cancel() {
        val dialogDismissService: DialogDismissService by App.getKodein().instance()
        dialogDismissService.dismiss(ModifyWalletNEUDialog::class.java)
    }
}