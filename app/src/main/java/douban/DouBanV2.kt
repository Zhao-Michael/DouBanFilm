package douban

import util.GetUrlContent
import util.fromURL

object DouBanV2 {

    const val mBaseUrl = "https://frodo.douban.com/api/v2/movie"
    const val mApiKey = "apikey=0b2bdeda43b5688921839c8ecb20399b"
    const val mUserAgent = "api-client/1 com.douban.movie/4.5.0(81) com.douban.frodo/5.11.0(114) Android/23 product/gemini vendor/Xiaomi model/MI 5  rom/miui6  network/wifi"
    const val mSignature = "&_sig=%2FFkXnyeB2yTutvoY%2BW%2BgmKfLqzo%3D&_ts=1538882248"

    fun getFilmPhoto(id: String, start: Int = 0, count: Int = 50) {
        val url = "$mBaseUrl/26425063/photos?start=$start&count=$count&$mApiKey&$mSignature"
        val html = GetUrlContent(url, userAgent = mUserAgent).fromURL()
        println(html)
    }


}