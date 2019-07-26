package com.dingdonginc.zhangfang.viewmodels

import android.graphics.Bitmap
import android.os.AsyncTask
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.dingdonginc.zhangfang.services.neuspider.GetChapter
import com.dingdonginc.zhangfang.services.neuspider.NEUSpider
import java.lang.ref.WeakReference

class ModifyWalletNEUViewModel: ViewModel() {
    val loadingCaptcha = ObservableField<Boolean>(true)
    val username = ObservableField<String>("")
    val password = ObservableField<String>("")
    val captcha = ObservableField<String>("")
    val captchaImage = ObservableField<Bitmap>()

    init {
        refreshCaptcha()
    }

    fun refreshCaptcha() {
        val refresh = RefreshCaptcha(WeakReference(loadingCaptcha), WeakReference(captchaImage))
        refresh.execute()
    }

    private class RefreshCaptcha(
        private val loading: WeakReference<ObservableField<Boolean>>,
        private val image: WeakReference<ObservableField<Bitmap>>
    ): AsyncTask<Unit, Unit, Bitmap>() {
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
        GetNEUCard(username.get()!!, password.get()!!, captcha.get()!!).execute()
    }

    private class GetNEUCard(val uid: String, val psd: String, val captcha: String):
            AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg p0: Unit?) {
            val neuSpider = NEUSpider()
            neuSpider.login(uid, psd, captcha)

            val startDate = "2019-07-18"
            val endDate = "2019-07-25"
            val list = neuSpider.get(startDate, endDate)
            list.forEach{
                Log.d("NEUWALLET", it.toString())
            }
        }
    }
}