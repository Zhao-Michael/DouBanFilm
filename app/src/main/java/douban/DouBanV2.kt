package douban

import com.google.gson.Gson
import database.NetRequestType
import org.jsoup.Jsoup
import util.GetUrlContent
import util.Util.GetRegexList
import util.fromJson
import kotlin.math.min

//About Html
object DouBanV2 {

    private const val mBaseUrl = "https://movie.douban.com/"
    private const val mSearchTag = "j/search_subjects"

    enum class FilmTagType {
        Hot, New, Classic, Play, High, Rare, China, West, Korea, Japan, Action, Comedy, Love, Science, Suspense, Horror, Literary
    }

    enum class TVTagType {
        Hot, US, GB, KR, JP, MainLand, HK, JPAnimation, Show, Documentary
    }

    fun getTagString(tvTagType: TVTagType): String {
        return when (tvTagType) {
            TVTagType.Hot -> "热门"
            TVTagType.US -> "美剧"
            TVTagType.GB -> "英剧"
            TVTagType.KR -> "韩剧"
            TVTagType.JP -> "日剧"
            TVTagType.MainLand -> "国产剧"
            TVTagType.HK -> "港剧"
            TVTagType.JPAnimation -> "日本动画"
            TVTagType.Show -> "综艺"
            TVTagType.Documentary -> "纪录片"
        }
    }

    fun getTagString(filmTag: FilmTagType): String {
        return when (filmTag) {
            DouBanV2.FilmTagType.Hot -> "热门"
            DouBanV2.FilmTagType.New -> "最新"
            DouBanV2.FilmTagType.Classic -> "经典"
            DouBanV2.FilmTagType.Play -> "可播放"
            DouBanV2.FilmTagType.High -> "豆瓣高分"
            DouBanV2.FilmTagType.Rare -> "冷门佳片"
            DouBanV2.FilmTagType.China -> "华语"
            DouBanV2.FilmTagType.West -> "欧美"
            DouBanV2.FilmTagType.Korea -> "韩国"
            DouBanV2.FilmTagType.Japan -> "日本"
            DouBanV2.FilmTagType.Action -> "动作"
            DouBanV2.FilmTagType.Comedy -> "喜剧"
            DouBanV2.FilmTagType.Love -> "爱情"
            DouBanV2.FilmTagType.Science -> "科幻"
            DouBanV2.FilmTagType.Suspense -> "悬疑"
            DouBanV2.FilmTagType.Horror -> "恐怖"
            DouBanV2.FilmTagType.Literary -> "文艺"
        }
    }

    private fun getTagContent(tag: String, start: Int = 0, count: Int = 30, isFilm: Boolean = true): TagFilmList {
        val url = "$mBaseUrl$mSearchTag?type=${if (isFilm) "movie" else "tv"}&tag=$tag&page_limit=$count&page_start=$start"
        val html = GetUrlContent(url, NetRequestType.Day)
        return Gson().fromJson(html)
    }

    fun getTagFilm(filmTag: FilmTagType, start: Int = 0, count: Int = 30): TagFilmList {
        return getTagContent(getTagString(filmTag), start, count)
    }

    fun getTagFilm(tag: String, start: Int = 0, count: Int = 30): TagFilmList {
        return getTagContent(tag, start, count)
    }

    fun getTagTV(filmTag: TVTagType, start: Int = 0, count: Int = 30): TagFilmList {
        return getTagContent(getTagString(filmTag), start, count, false)
    }

    fun getTagTV(tag: String, start: Int = 0, count: Int = 30): TagFilmList {
        return getTagContent(tag, start, count, false)
    }


    fun getFilmPhoto(id: String, start: Int = 0): List<Photo> {
        val url = "${mBaseUrl}subject/$id/photos?type=S&start=$start"
        val html = GetUrlContent(url, NetRequestType.Day)
        return parseFilmPhoto(html)
    }

    private fun parseFilmPhoto(html: String): List<Photo> {
        val list = mutableListOf<Photo>()
        val listImg = GetRegexList(html, "<img src=\"https(.+?)\" />", "<img src=\"", "\" />")

        listImg.forEachIndexed { index, it ->
            try {
                val t_id = it.split("public/p")[1].split(".")[0]
                val photo = Photo(listImg.size, "", "", Author.NullAuthor, "",
                        t_id, it.replace("/m/", "/sqs/"),
                        t_id, "", "", 0,
                        it.replace("/m/", "/l/"),
                        0, index, "", "", "", "", "")
                list.add(photo)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        return list
    }

    fun getFilmPhotoComment(id: String, start: Int = 0): List<PhotoComment> {
        val backUrl = if (start == 0) "#comments" else "?start=$start#comments"
        val url = "${mBaseUrl}photos/photo/$id/$backUrl"
        val html = GetUrlContent(url, NetRequestType.Day)
        return parseFilmPhotoComment(html)
    }

    private fun parseFilmPhotoComment(html: String): List<PhotoComment> {

        val text = html.split("<div id=\"comments\" class=\"\">")[1].split("<div class=\"paginator\">")[0]

        val list = mutableListOf<PhotoComment>()

        val thums = GetRegexList(text, "src=\"https(.+?)\" alt=\"", "src=\"", "\" alt=\"")
        val authors = GetRegexList(text, "alt=\"(.+?)\"/></a>", "alt=\"", "\"/></a>")
        val contents = GetRegexList(text, "<p class=\"\">(.+?)</p>", "<p class=\"\">", "</p>")
        val times = GetRegexList(text, "<span class=\"\">(.+?)</span>", "<span class=\"\">", "</span>")

        val min = min(min(min(thums.size, authors.size), contents.size), times.size)

        (0 until min).forEach {
            list.add(PhotoComment(thums[it], authors[it], contents[it], times[it]))
        }

        return list
    }

    fun getCelebrityWorkList(celebrityID: String, isByTime: Boolean, start: Int = 0, count: Int = 10): FilmList {
        val url = "${mBaseUrl}/celebrity/${celebrityID}/movies?start=${start}&sortby=${if (isByTime) "time" else "vote"}"
        val html = GetUrlContent(url)
        return getCelebrityWork(start, html)
    }

    private fun getCelebrityWork(start: Int, html: String): FilmList {
        val list = mutableListOf<FilmItem>()
        val doc = Jsoup.parse(html)
        val films = doc.select("li dl dt a")
        films.forEach { t ->
            val it = t.parent().parent()
            val id = it.select("a.nbg").attr("href").trim('/').split('/').last()
            val thum_url = it.selectFirst("img").attr("src")
            val title = it.selectFirst("img").attr("alt")
            val origin_title = it.selectFirst("img").attr("title")
            val year = it.selectFirst("span").text().replace("(", "").replace(")", "")
            val isHasRate = it.selectFirst("div").children().size > 1
            val rate = if (isHasRate) it.selectFirst("div").child(1).text().toDouble() else 0.0
            val person = it.selectFirst("dd dl").allElements
            var director = ""
            var actor = ""
            if (person.size == 5) {
                director = person[2].text().trim()
                actor = person[4].text().trim()
            } else if (person.size == 4) {
                director = person[1].text().trim()
                actor = person[3].text().trim()
            }

            val rating = Rating(10, rate, "1", 0, Details())
            val genres = emptyList<String>()
            val listActor = mutableListOf(Celebrity(Avatars("", "", ""), "", actor, "", ""))
            val listDirector = mutableListOf(Celebrity(Avatars("", "", ""), "", director, "", ""))
            val image = Images(thum_url, thum_url, thum_url)
            val item = FilmItem(rating, genres, title, listActor, 0, origin_title, "movie", listDirector, year, image, "", id)

            list.add(item)
        }
        val title = doc.title()
        var total = 1000
        if (title.endsWith("）")) {
            total = title.split("（").last().split("）").first().toInt()
        }

        return FilmList(films.size, start, total, list, "CelebrityWorkList")
    }


}