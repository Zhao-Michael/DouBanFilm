package netsearch

import util.GetUrlContent
import util.Util

object BTSearch {

    enum class SearchType { CTIME, LENGTH, CLICK, }

    data class BtItem(val url: String, val title: String, val date: String, val length: String, val view: String)

    data class SearchResult(val item: List<BtItem>, val page: Int, val type: SearchType)

    data class BTDetail(val title: String, val length: String, val date: String, val view: String, val link: String, val files: List<String>, val hotKeys: List<String>)

    const val mBaseUrl = "https://www.bturl.pw/"

    fun searchBT(key: String, type: SearchType = SearchType.CLICK, page: Int = 1): SearchResult {

        val url = "${mBaseUrl}search/${key}_${type.toString().toLowerCase()}_$page.html"

        val html = GetUrlContent(url)

        val list = parseBTList(html)

        return SearchResult(list, page, type)
    }

    fun getBTDetail(url: String): BTDetail {
        val html = GetUrlContent(url)
        return parseBTDetail(html)
    }

    private fun parseBTList(html: String): List<BtItem> {
        if (html.length < 100) return emptyList()
        val result = mutableListOf<BtItem>()
        val urls = Util.GetRegexList(html, "_blank\" href=\"(.+?)\">", "_blank\" href=\"/", "\">").map { mBaseUrl + it }
        val titles = Util.GetRegexList(html, "item-list\">(.+)", "item-list\">")
        val dates = Util.GetRegexList(html, "创建日期：<span>(.+?)<", "创建日期：<span>", "<")
        val length = Util.GetRegexList(html, "文件大小：<span>(.+?)<", "文件大小：<span>", "<")
        val views = Util.GetRegexList(html, "访问热度：<span>(.+?)<", "访问热度：<span>", "<")
        urls.forEachIndexed { i, _ ->
            result.add(BtItem(urls[i], titles[i], dates[i], length[i], views[i]))
        }
        return result
    }

    private fun parseBTDetail(html: String): BTDetail {
        if (html.length < 100) return BTDetail("", "", "", "", "", emptyList(), emptyList())

        val title = Util.GetRegexList(html, "class=\"T1\">(.+?)<", "class=\"T1\">", "<").first()
        val length = Util.GetRegexList(html, "文件大小:(.+?)<", "文件大小:", "<").first()
        val date = Util.GetRegexList(html, "创建日期:(.+?)<", "创建日期:", "<").first()
        val view = Util.GetRegexList(html, "访问热度:(.+?)<", "访问热度:", "<").first()
        val link = Util.GetRegexList(html, "磁力链接: <a href=\"(.+?)\">", "磁力链接: <a href=\"", "\">").first()

        val filesText = html.split("<ol class=\"flist\">")[1].split("<div class=\"rightadbox\"")[0]
        val hotText = html.split("<div class=\"hotkbox\">")[1].split("<div id=\"footer\">")[0]

        val files = Util.GetRegexList(filesText, "<li>(.+?)<", "<li>", "<")
        val lengths = Util.GetRegexList(filesText, "<span>(.+?)<", "<span>", "<")
        val listFiles = files.mapIndexed { i, s -> "[${lengths[i]}] $s" }

        val hotKeys = Util.GetRegexList(hotText, "\">(.+?)<", "\">", "<").map { it.trim() }.distinct()

        return BTDetail(title, length, date, view, link, listFiles, hotKeys)
    }


}