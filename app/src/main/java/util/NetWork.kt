package util

import database.NetRequestType
import database.NetWorkCache
import database.NetWorkRequest
import database.DBConstUtil.getMilliSecond
import michaelzhao.App.Companion.UserAge
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import util.Util.NowDate
import java.util.concurrent.TimeUnit

const val UNIT_TEST = false

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
            println("From Net Response [${result.length}] : " + result.trim().removeCrlf().take(100) + "...")
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

fun GetBytesFromNet(url: String, userAgent: String = UserAge): ByteArray {
    var response: Response? = null
    try {
        val real_url = url.replace(Regex("\\s+"), "+")
        val request = Request.Builder().url(real_url).addHeader("User-Agent", userAgent).build()
        response = okhttp.newCall(request).execute()
        val body = response.body()
        if (body != null) {
            return body.bytes()
        }
    } catch (ex: Exception) {
        println("From Net Error: $ex  $url")
    } finally {
        response?.close()
    }
    return ByteArray(0)
}

fun GetUrlContent(url: String, type: NetRequestType = NetRequestType.Day, userAgent: String = UserAge): String {
    if (UNIT_TEST) return GetUrlContentForTest(url)

    return Util.TimeElapse("Get Url Content") {
        println("Create Net Request: $url")
        val data = isNeedNewRequest(url, type)
        if (data.first) {
            val result = downLoadString(url, userAgent, type)
            if (result.isBlank() && data.second.isNotBlank()) {
                println("From DataBase Response [${data.first}] : " + data.second.trim().removeCrlf().take(100) + "...")
                return@TimeElapse data.second
            }
            return@TimeElapse result
        } else {
            println("From DataBase Response [${data.first}] : " + data.second.trim().removeCrlf().take(100) + "...")
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

            return Pair(false, data.content)
        }
    }
    return Pair(true, "")
}

fun GetUrlContentForTest(url: String): String {
    var result = ""
    try {
        val real_url = url.replace(Regex("\\s+"), "+")
        val request = Request.Builder().url(real_url).build()
        val response = okhttp.newCall(request).execute()
        val body = response.body()
        if (body != null) {
            result = body.string()
            println("From Net Response [${result.length}] : " + result.trim().removeCrlf().take(100) + "...")
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
