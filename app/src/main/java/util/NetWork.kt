package util

import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

val okhttp: OkHttpClient
    get() {
        return OkHttpClient().newBuilder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .readTimeout(20000, TimeUnit.MILLISECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
                .build()
    }

fun DownLoadString(url: String): String {
    var result = ""
    try {
        val real_url = url.replace(Regex("\\s+"), "+")
        println("Request: $real_url")
        val request = Request.Builder().url(real_url).build()
        val response = okhttp.newCall(request).execute()
        result = response.body()?.string()!!
        if (result.isNotBlank()) {
            println("Response [${result.length}] : " + result.trim().take(100) + "...")
        }
        response.close()
        return result
    } catch (ex: Exception) {
        println("DownLoadString : $ex  $url")
    }
    return result
}