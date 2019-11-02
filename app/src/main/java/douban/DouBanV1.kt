package douban

import com.google.gson.Gson
import org.jsoup.Jsoup
import util.GetUrlContent
import util.*


object DouBanV1 {

    const val mBaseUrl = "https://api.douban.com/v2/movie/"
    const val mApiKey = "apikey=0b2bdeda43b5688921839c8ecb20399b"
    const val mIPCity = "http://pv.sohu.com/cityjson"


    //获取影片介绍
    fun getFilmDetail(id: String): FilmDetail {
        val url = "${mBaseUrl}subject/$id?$mApiKey"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取电影剧照
    fun getFilmPhoto(id: String, start: Int = 0, count: Int = 30): FilmPhoto {
        val url = "${mBaseUrl}subject/$id/photos?$mApiKey&start=$start&count=$count"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取电影短评
    fun getFilmComment(id: String, start: Int = 0, count: Int = 30): FilmComment {
        val url = "${mBaseUrl}subject/$id/comments?$mApiKey&start=$start&count=$count"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取电影影评
    fun getFilmReview(id: String, start: Int = 0, count: Int = 30): FilmReview {
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
    fun getSearchFilmList(key: String, start: Int = 0, count: Int = 30): FilmList {
        val url = "${mBaseUrl}search?q=$key&start=$start&count=$count"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取指定标记相关的电影列表
    fun getTagFilmList(tag: String, start: Int = 0, count: Int = 30): FilmList {
        val url = "${mBaseUrl}search?tag=$tag&start=$start&count=$count"
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
    fun getTheaterFilms(city: String, start: Int = 0, count: Int = 40): FilmList {
        val url = "${mBaseUrl}in_theaters?$mApiKey&city=${city.toURL()}&start=$start&count=$count"
        val html = GetUrlContent(url)
        return Gson().fromJson(html)
    }

    //获取豆瓣 Top 250
    fun getTop250Film(start: Int = 0, count: Int = 25): FilmList {
        val url = "https://movie.douban.com/top250?start=$start&count=25"
        val html = GetUrlContent(url)
        return paresTop250Films(start, html)
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

    private fun paresTop250Films(start: Int, html: String): FilmList {
        val list = mutableListOf<FilmItem>()

        val doc = Jsoup.parse(html)
        val films = doc.select("ol li")
        films.forEach {
            val thum_url = it.select("img").attr("src")
            val title = it.select(".title").first().text().trim()
            val id = it.select(".hd").first().child(0).attr("href").trim('/').split("/").last()
            val rate = it.select(".rating_num").first().text().toDouble()
            val detail = it.select(".bd").first().child(0).text()
            val origin_title = it.select(".other").first().text().trim('/')
            val director = detail.split("导演:").last().split("主演:").first()
            val actor = detail.split("主演:").last().split("...").first().trim('/')
            val year = detail.split("...").last().split("/").first().trim()
            var type = detail.split("\n").last().split("/").last().trim('/')
            if (type.startsWith("/")) type = type.drop(1)

            val rating = Rating(10, rate, "1", 0, Details())
            val genres = type.split(" ")
            val listActor = mutableListOf(Celebrity(Avatars("", "", ""), "", actor, "", ""))
            val listDirector = mutableListOf(Celebrity(Avatars("", "", ""), "", director, "", ""))
            val image = Images(thum_url, thum_url, thum_url)
            val item = FilmItem(rating, genres, title, listActor, 0, origin_title, "movie", listDirector, year, image, "", id)

            list.add(item)
        }

        return FilmList(25, start, 25, list, "Top250")
    }


}