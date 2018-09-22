package util

import okhttp3.OkHttpClient
import okhttp3.Request
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

private fun DownLoadString(url: String): String {
    var result = ""
    try {
        val real_url = url.replace(Regex("\\s+"), "+")
        println("Net Request: $real_url")
        val request = Request.Builder().url(real_url).build()
        val response = okhttp.newCall(request).execute()
        result = response.body()?.string()!!
        if (result.isNotBlank()) {
            println("Net Response [${result.length}] : " + result.trim().take(100) + "...")
        }
        response.close()
        return result
    } catch (ex: Exception) {
        println("Get Content From Net : $ex  $url")
    }
    return result
}

fun GetUrlContent(url: String, type: TimeType = TimeType.Day): String {
    return TimeElapse("Get Url Content") {
        DownLoadString(url)
    }
}