package douban

import com.google.gson.Gson
import database.NetRequestType
import util.GetUrlContent
import util.fromJson

//About Html
object DouBanV2 {

    private const val mBaseUrl = "https://movie.douban.com/"
    private const val mSearchTag = "j/search_subjects?"

    enum class TagType {
        Hot, New, Classic, Play, High, Rare, China, West, Korea, Japan, Action, Comedy, Love, Science, Suspense, Horror, Literary
    }

    fun getTagString(tag: TagType): String {
        return when (tag) {
            DouBanV2.TagType.Hot -> "热门"
            DouBanV2.TagType.New -> "最新"
            DouBanV2.TagType.Classic -> "经典"
            DouBanV2.TagType.Play -> "可播放"
            DouBanV2.TagType.High -> "豆瓣高分"
            DouBanV2.TagType.Rare -> "冷门佳片"
            DouBanV2.TagType.China -> "华语"
            DouBanV2.TagType.West -> "欧美"
            DouBanV2.TagType.Korea -> "韩国"
            DouBanV2.TagType.Japan -> "日本"
            DouBanV2.TagType.Action -> "动作"
            DouBanV2.TagType.Comedy -> "喜剧"
            DouBanV2.TagType.Love -> "爱情"
            DouBanV2.TagType.Science -> "科幻"
            DouBanV2.TagType.Suspense -> "悬疑"
            DouBanV2.TagType.Horror -> "恐怖"
            DouBanV2.TagType.Literary -> "文艺"
        }
    }

    fun getTagFilm(tag: TagType, start: Int = 0, count: Int = 30): TagFilmList {
        val url = "$mBaseUrl$mSearchTag?type=movie&tag=${getTagString(tag)}&sort=time&page_limit=$count&page_start=$start"
        val html = GetUrlContent(url, NetRequestType.Second)
        return Gson().fromJson(html)
    }

    fun getTagFilm(tag: String, start: Int = 0, count: Int = 30): TagFilmList {
        val url = "$mBaseUrl$mSearchTag?type=movie&tag=$tag&sort=time&page_limit=$count&page_start=$start"
        val html = GetUrlContent(url, NetRequestType.Second)
        return Gson().fromJson(html)
    }

}