package util

import database.NetRequestType
import database.NetWorkCache
import database.NetWorkRequest
import database.NetWorkRequestUtil.getMilliSecond
import michaelzhao.App.Companion.UserAge
import okhttp3.OkHttpClient
import okhttp3.Request
import util.Util.NowDate
import java.security.KeyPair
import java.util.*
import java.util.concurrent.TimeUnit

enum class TimeType {
    Second,
    Minute,
    Hour,
    HalfDay,
    Day,
    Week,
    Month,
    HalfYear,
    Year,
    TenYear,
    Indefinite
}

private val okhttp: OkHttpClient
    get() {
        return OkHttpClient().newBuilder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .readTimeout(20000, TimeUnit.MILLISECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
                .build()
    }

private fun downLoadString(url: String, userAgent: String = UserAge, type: NetRequestType = NetRequestType.Day): String {
    var result = ""
    try {
        val real_url = url.replace(Regex("\\s+"), "+")
        val request = Request.Builder().url(real_url).addHeader("User-Agent", userAgent).build()
        val response = okhttp.newCall(request).execute()
        val body = response.body()
        if (body != null) {
            result = body.string()
            NetWorkCache.Instance.addNetRequest(url, result, type)
            println("From Net Response [${result.length}] : " + result.trim().take(100) + "...")
        } else {
            println("From Net Error: Empty Response Body  $url")
        }
        response.close()
        return result
    } catch (ex: Exception) {
        println("From Net Error: $ex  $url")
    }
    return result
}

fun GetUrlContent(url: String, type: NetRequestType = NetRequestType.Day, userAgent: String = UserAge): String {
    return Util.TimeElapse("Get Url Content") {
        println("Create Net Request: $url")
        val data = isNeedNewRequest(url, type)
        if (data.first) {
            downLoadString(url, userAgent, type)
        } else {
            data.second
        }
    }
}

private fun isNeedNewRequest(url: String, type: NetRequestType): Pair<Boolean, String> {
    val data = NetWorkCache.Instance.findUrl(url)
    val now = NowDate
    if (data != NetWorkRequest.NULL) {
        val duration = now.time - data.time.time
        val typeMS = getMilliSecond(type)
        if (duration <= typeMS) {
            println("From DataBase Response [${data.content}] : " + data.content.trim().take(100) + "...")
            return Pair(false, data.content)
        }
    }
    return Pair(true, "")
}


