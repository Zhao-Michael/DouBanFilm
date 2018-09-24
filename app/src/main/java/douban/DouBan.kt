package douban

import com.google.gson.Gson
import util.GetUrlContent
import util.*


val Douban = DouBan()

class DouBan {
    private val mBaseUrl = "https://api.douban.com/v2/movie/"
    private val mApiKey = "apikey=0b2bdeda43b5688921839c8ecb20399b"
    private val mIPCity = "http://pv.sohu.com/cityjson"

    //获取影片介绍
    fun getFilmDetail(id: String): FilmDetail {
        val url = "${mBaseUrl}subject/$id?$mApiKey"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取电影剧照
    fun getFilmPhoto(id: String, start: Int = 0, count: Int = 100): FilmPhoto {
        val url = "${mBaseUrl}subject/$id/photos?$mApiKey&start=$start&count=$count"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取电影短评
    fun getFilmComment(id: String, start: Int = 0, count: Int = 100): FilmComment {
        val url = "${mBaseUrl}subject/$id/comments?$mApiKey&start=$start&count=$count"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取电影影评
    fun getFilmReview(id: String, start: Int = 0, count: Int = 100): FilmReview {
        val url = "${mBaseUrl}subject/$id/reviews?$mApiKey&start=$start&count=$count"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取电影简短信息，用于搜索提示
    fun getSearchBrief(key: String): Array<SearchBrief> {
        val url = "https://movie.douban.com/j/subject_suggest?q=$key"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取当前 IP 所在城市
    fun getCurrentCity(): IPCity {
        val url = mIPCity
        val html = GetUrlContent(url)
        if (html.isBlank() || !html.contains("var returnCitySN = ")) return IPCity("", "", "")
        return Gson().fromJson(html.removePrefix("var returnCitySN = ").removeSuffix(";"))
    }

    //获取城市列表
    fun getCityList(start: Int = 0, count: Int = 100): CityList {
        val url = "https://api.douban.com/v2/loc/list?&start=$start&count=$count"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取电影搜索列表
    fun getSearchFilmList(key: String, start: Int = 0, count: Int = 100): FilmList {
        val url = "${mBaseUrl}search?q=$key&start=$start&count=$count"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取影人信息
    fun getFilmManInfo(id: String): FilmMan {
        val url = "${mBaseUrl}celebrity/$id"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取影人作品
    fun getFilmManWork(id: String, start: Int = 0, count: Int = 30): FilmManWork {
        val url = "${mBaseUrl}celebrity/$id/works?$mApiKey&start=$start&count=$count"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取影人照片
    fun getFilmManPhoto(id: String, start: Int = 0, count: Int = 30): FilmManPhoto {
        val url = "${mBaseUrl}celebrity/$id/photos?$mApiKey&start=$start&count=$count"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取院线电影
    fun getTheaterFilms(city: String, start: Int = 0, count: Int = 100): FilmList {
        val url = "${mBaseUrl}in_theaters?$mApiKey&city=${city.toURL()}&start=$start&count=$count"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取豆瓣 Top 250
    fun getTop250Film(start: Int = 0, count: Int = 250): FilmList {
        val url = "${mBaseUrl}top250?&start=$start&count=$count"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取北美票房榜
    fun getUSFilmRank(): RankFilm {
        val url = "${mBaseUrl}us_box?$mApiKey"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取周榜
    fun getWeeklyRank(): RankFilm {
        val url = "${mBaseUrl}weekly?$mApiKey"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取新片榜
    fun getNewFilmRank(): FilmList {
        val url = "${mBaseUrl}new_movies?$mApiKey"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取即将上映
    fun getComingFilm(): FilmList {
        val url = "${mBaseUrl}coming_soon?$mApiKey"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

}