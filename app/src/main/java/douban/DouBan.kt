package douban

import com.google.gson.Gson
import util.DownLoadString
import util.*


val Douban = DouBan()

class DouBan {
    private val mBaseUrl = "https://api.douban.com/v2/movie/"
    private val mApiKey = "apikey=0b2bdeda43b5688921839c8ecb20399b"

    //获取院线电影
    fun getTheaterFilms(city: String, start: Int = 0, count: Int = 100): FilmList {
        val url = "${mBaseUrl}in_theaters?$mApiKey&city=${city.toURL()}&start=$start&count=$count"
        val html = DownLoadString(url)
        return Gson().fromJson(html)
    }

    //获取影片介绍
    fun getFilmDetail(id: String): FilmDetail {
        val url = "${mBaseUrl}subject/$id?$mApiKey"
        val html = DownLoadString(url)
        return Gson().fromJson(html)
    }

    //获取电影图片
    fun getFilmPhoto(id: String, start: Int = 0, count: Int = 100): FilmPhoto {
        val url = "${mBaseUrl}subject/$id/photos?$mApiKey&start=$start&count=$count"
        val html = DownLoadString(url)
        return Gson().fromJson(html)
    }

    //获取电影短评
    fun getFilmComment(id: String, start: Int = 0, count: Int = 100): FilmComment {
        val url = "${mBaseUrl}subject/$id/comments?$mApiKey&start=$start&count=$count"
        val html = DownLoadString(url)
        return Gson().fromJson(html)
    }

    //获取电影影评
    fun getFilmReview(id: String, start: Int = 0, count: Int = 100): FilmReview {
        val url = "${mBaseUrl}subject/$id/reviews?$mApiKey&start=$start&count=$count"
        val html = DownLoadString(url)
        return Gson().fromJson(html)
    }

}