package com.dingdonginc.zhangfang.services.neuspider

import android.util.Log
import com.dingdonginc.zhangfang.services.Utils
import org.jsoup.Jsoup
import java.io.ByteArrayOutputStream
import java.lang.IndexOutOfBoundsException
import java.net.*
import java.util.regex.Pattern

class NEUSpider {
    private val TAG = "NEUSpider"
    private val baseHeader: HashMap<String, String> = hashMapOf(
        "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0",
        "Accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
        "Accept-Language" to "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2",
        "Accept-Encoding" to "gzip, deflate",
//        "Referer" to "http://ecard.neu.edu.cn/SelfSearch/User/ConsumeInfo.aspx",
        "Content-Type" to "application/x-www-form-urlencoded",
        "Connection" to "keep-alive",
        "Upgrade-Insecure-Requests" to "1",
        "Pragma" to "no-cache",
        "Cache-Control" to "no-cache"
    )


    private fun getLoginForm(): HashMap<String, String> {
        val url = URL("http://ecard.neu.edu.cn/selfsearch/login.aspx")
        val con = url.openConnection() as HttpURLConnection
        con.requestMethod = "GET"
        baseHeader.forEach{con.setRequestProperty(it.key, it.value)}
        con.connect()
        val soup = Jsoup.parse(con.inputStream, "UTF-8", "http://ecard.neu.edu.cn/selfsearch/login.aspx")
        val inputs = soup.select("#form2 input")
//        Log.d(TAG, "get input form $inputs")
        val loginForm = inputs.fold(HashMap<String, String>(), {a, e ->
            a[e.attr("name")] = e.attr("value")
            a
        })
        loginForm.forEach{println(it)}
        return loginForm
    }

    private fun tryLogin(loginForm: Map<String, String>): String? {
        Log.d(TAG, "Login uid:${loginForm["txtUserName"]} psw:${loginForm["txtPassword"]} captcha:${loginForm["txtVaildateCode"]}")
        val url = URL("http://ecard.neu.edu.cn/SelfSearch/Login.aspx")
        val con = url.openConnection() as HttpURLConnection
        con.requestMethod = "POST"
        con.doInput = true
        con.doOutput = true
        baseHeader.forEach{con.setRequestProperty(it.key, it.value)}
        con.setRequestProperty("Referer", "http://ecard.neu.edu.cn/SelfSearch/login.aspx")
//        val cookie = CookieManager.getInstance()
//        Log.d(TAG, "login cookie: $cookie")
//        con.setRequestProperty("Cookie", cookie.getCookie("ecard.neu.edu.cn"))
        con.connect()
//        val loginDataList = loginForm.map {
//            "${URLEncoder.encode(it.key, "utf-8")}=${URLEncoder.encode(it.value, "utf-8")}" }
//        val loginData = loginDataList.joinToString("&")
        val loginData = Utils.urlEncodeFormData(loginForm)
        val os = con.outputStream
        os.write(loginData.toByteArray())
        os.flush()
        os.close()
        Log.d(TAG, "Login response code ${con.responseCode}")

        val ist = con.inputStream
        val baos = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        while (ist.read(buffer) != -1) {
            baos.write(buffer)
        }
        con.disconnect()
        val response = baos.toString("utf-8")
        val errstr1 = "验证码错误，请输入正确的验证码。"
        if ("消费信息查询" in response) {
            Log.i("NEUSpider", "登录成功")
            return null
        } else {
            Log.d(TAG, "Login response ${response}")
            return "未知错误"
        }
    }

    private fun getUserInfo() {
        val url = URL("http://ecard.neu.edu.cn/SelfSearch/User/Home.aspx")
        val con = url.openConnection() as HttpURLConnection
        baseHeader.forEach{con.setRequestProperty(it.key, it.value)}
        val soup = Jsoup.parse(con.inputStream, "utf-8", "http://ecard.neu.edu.cn/SelfSearch/User/")
        try {
            val div = soup.select(".person_news")[0]
            val ul = div.select("ul")
            for (x in ul.select("span")) {
                Log.d("li", x.html())
            }
        } catch (e : IndexOutOfBoundsException) {
            Log.i("getUserInfo", "no person_news in response")
            e.printStackTrace()
        }
    }

    private fun getWalletPage(): HashMap<String, String> {
        val url = URL("http://ecard.neu.edu.cn/SelfSearch/User/ConsumeInfo.aspx")
        val con = url.openConnection() as HttpURLConnection
        val soup = Jsoup.parse(con.inputStream, "utf-8", "http://ecard.neu.edu.cn/SelfSearch/User/")
        return soup.select("#form1 input").fold(HashMap(), {
            acc, element ->
            acc[element.attr("name")] = element.attr("value")
            acc
        })
    }

    private fun getWallet(pageForm: HashMap<String, String>) : List<NEUWallet> {
        val data = Utils.urlEncodeFormData(pageForm)
        val url = URL("http://ecard.neu.edu.cn/SelfSearch/User/ConsumeInfo.aspx")
        val con = url.openConnection() as HttpURLConnection
        baseHeader.forEach{ con.setRequestProperty(it.key, it.value) }
        con.doInput = true
        con.doOutput = true
        con.requestMethod = "POST"
        val os = con.outputStream
        os.write(data.toByteArray())
        os.flush()
        os.close()
        Log.d(TAG, "Login response code ${con.responseCode}")

//        val ist = con.inputStream
//        val baos = ByteArrayOutputStream()
//        val buffer = ByteArray(8192)
//        while (ist.read(buffer) != -1) { baos.write(buffer) }
//        con.disconnect()
//        val response = baos.toString("utf-8")
//        println(response)
        val soup = Jsoup.parse(con.inputStream, "utf-8", "http://ecard.neu.edu.cn/SelfSearch/User/")
        soup.select("#form1 input").forEach { pageForm[it.attr("name")] = it.attr("value") }

        val ans = ArrayList<NEUWallet>()
        soup.select("#ContentPlaceHolder1_gridView tr").forEach { it->
            val tds = it.select("td")
            if (tds.size == 7 && tds[1].html() != "&nbsp;")
                ans.add(
                    NEUWallet(
                        time=tds[0].child(0).html(),
                        description = tds[1].html(),
                        amount = tds[2].html(),
                        remain = tds[3].html(),
                        operator = tds[4].html(),
                        station = tds[5].html(),
                        terminal = tds[6].html()
                    )
                )
        }

        // 处理下一页
        val navigationBar = soup.select("#ContentPlaceHolder1_AspNetPager1")[0]
        // 倒数第二个是下一页按钮
        val nextBtn = navigationBar.children()[navigationBar.children().size - 2]
        assert(nextBtn.tagName() == "a")
        if (nextBtn.attr("disabled") != "true") {
            val js = nextBtn.attr("href")
            // javascript:__doPostBack('ctl00$ContentPlaceHolder1$AspNetPager1','3')
            val match = Pattern.compile(".+\\('(.+)','(.+)'\\)").matcher(js)
            if (match.find()) {
                pageForm["__EVENTTARGET"] = match.group(1)!!
                pageForm["__EVENTARGUMENT"] = match.group(2)!!
                pageForm.remove("ctl00\$ContentPlaceHolder1\$btnSearch")
                pageForm["ctl00\$ContentPlaceHolder1\$rbtnType"] = "0"
                Log.d("NEU wallet", "next page ${pageForm["__EVENTARGUMENT"]}")

                ans.addAll(getWallet(pageForm))
//                getWallet(pageForm)
            }
        }
        return ans
    }

    fun get(startDate: String, endDate: String): List<NEUWallet> {
        val pageForm = getWalletPage()
        pageForm["__EVENTTARGET"] = ""
        pageForm["__EVENTARGUMENT"] = ""
        pageForm["ctl00\$ContentPlaceHolder1\$rbtnType"] = "0"
        pageForm["ctl00\$ContentPlaceHolder1\$txtStartDate"] = startDate
        pageForm["ctl00\$ContentPlaceHolder1\$txtEndDate"] = endDate
        pageForm["ctl00\$ContentPlaceHolder1\$btnSearch"] = "查  询"
        return getWallet(pageForm)
    }

    fun login(uid: String, psd: String, captcha: String) {
        val loginForm = getLoginForm()
        loginForm["__EVENTTARGET"] = "btnlogin"
        loginForm["__EVENTARGUMENT"] = ""
        loginForm["__LASTFOCUS"] = ""
        loginForm["txtUserName"] = uid
        loginForm["txtPassword"] = psd
        loginForm["txtVaildateCode"] = captcha
        tryLogin(loginForm)
        getUserInfo()
    }
}


fun main(args: Array<String>) {
    val js = "javascript:__doPostBack('ctl00${'$'}ContentPlaceHolder1${'$'}AspNetPager1','3')"
    val match = Pattern.compile(".+\\('(.+)','(.+)'\\)").matcher(js)
    if (match.find()) {
        println(match.group(1))
        println(match.group(2))
    }
}