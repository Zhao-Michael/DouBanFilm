package database

import android.content.ContentValues
import database.DBConstUtil.CONTENT
import database.DBConstUtil.FORMATTER
import database.DBConstUtil.TIME
import database.DBConstUtil.EXPIRE
import database.DBConstUtil.URL
import org.apache.commons.lang3.NotImplementedException
import util.Util
import java.text.SimpleDateFormat
import java.util.*

enum class FavoriteType {
    Null,//Don't use it
    Film,
    Photo,
    Comment,
    Review,
}

enum class NetRequestType {
    Null,//Don't use it
    Zero,
    Second,
    Minute,
    HalfHour,
    Hour,
    HalfDay,
    Day,
    Week,
    Month,
    ThreeMonth,
    HalfYear,
    Year,
    FiveYear,
    TenYear,
    HundredYear,
    Infinite
}

object DBConstUtil {

    private const val Second = 1000L
    private const val Day = 24 * 60 * 60 * Second
    private const val Year = 365 * Day

    fun getMilliSecond(type: NetRequestType): Long {
        return when (type) {
            NetRequestType.Null -> throw NotImplementedException("Don't use NetRequestType.Null")
            NetRequestType.Zero -> 0.toLong()
            NetRequestType.Second -> Second
            NetRequestType.Minute -> 60 * Second
            NetRequestType.HalfHour -> 30 * 60 * Second
            NetRequestType.Hour -> 60 * 60 * Second
            NetRequestType.HalfDay -> Day / 2
            NetRequestType.Day -> Day
            NetRequestType.Week -> 7 * Day
            NetRequestType.Month -> 30 * Day
            NetRequestType.ThreeMonth -> 3 * 30 * Day
            NetRequestType.HalfYear -> Year / 2
            NetRequestType.Year -> Year
            NetRequestType.FiveYear -> 5 * Year
            NetRequestType.TenYear -> 10 * Year
            NetRequestType.HundredYear -> 100 * Year
            NetRequestType.Infinite -> Long.MAX_VALUE
        }
    }

    const val URL = "url"
    const val CONTENT = "content"
    const val TIME = "time"
    const val EXPIRE = "expire"
    val FORMATTER = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
}

data class NetWorkRequest(
        var url: String,
        var content: String,
        var time: Date,
        var type: NetRequestType) {

    companion object {
        val NULL = NetWorkRequest("", "", Date(), NetRequestType.Null)
    }

    fun fromSqlData(cv: ContentValues) {
        url = cv.getAsString(URL)
        content = cv.getAsString(CONTENT)
        time = FORMATTER.parse(cv.getAsString(TIME))
        type = NetRequestType.valueOf(cv.getAsString(EXPIRE))
    }

    fun toSqlData(): ContentValues {
        val cv = ContentValues()
        cv.put(URL, url)
        cv.put(CONTENT, Util.Compress(content))
        cv.put(TIME, FORMATTER.format(time))
        cv.put(EXPIRE, type.toString())
        return cv
    }

    override fun equals(other: Any?): Boolean {
        if (other is NetWorkRequest) {
            if (type == other.type &&
                    time == other.time &&
                    url == other.url &&
                    content == other.content)
                return true
        }
        return false
    }

    override fun hashCode(): Int {
        var result = url.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }

}