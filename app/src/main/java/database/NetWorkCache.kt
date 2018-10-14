package database

import android.database.Cursor
import database.NetWorkRequestUtil.CONTENT
import database.NetWorkRequestUtil.TIME
import database.NetWorkRequestUtil.TYPE
import database.NetWorkRequestUtil.URL
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import database.NetWorkRequestUtil.FORMATTER
import michaelzhao.App
import util.Util.NowDate

class NetWorkCache : SQLiteOpenHelper(App.Instance, DATABASE_FILENAME, null, 1) {

    companion object {
        private const val DATABASE_FILENAME = "GitFilmNetWorkDataCache.db"
        private const val TABLE_REQUEST = "NetworkRequestCache"
        private const val CREATE_NETWORK_DATABASE_CACHE: String = "create table $TABLE_REQUEST (" +
                "id integer primary key autoincrement, " +
                "$URL text, " +
                "$CONTENT text, " +
                "$TIME text, " +
                "$TYPE text )"

        val Instance by lazy { NetWorkCache() }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL(CREATE_NETWORK_DATABASE_CACHE)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) = Unit

    fun addNetRequest(request: NetWorkRequest) {
        val db = writableDatabase
        try {
            if (existUrl(request.url)) {
                db.update(TABLE_REQUEST, request.toSqlData(), "$URL=?", arrayOf(request.url))
                println("DateBase[$TABLE_REQUEST] : update ${request.url} into cache database")
            } else {
                db.insert(TABLE_REQUEST, null, request.toSqlData())
                println("DateBase[$TABLE_REQUEST] : insert ${request.url} into cache database")
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            db.close()
        }
    }

    fun addNetRequest(url: String, content: String, type: NetRequestType = NetRequestType.Day) {
        addNetRequest(NetWorkRequest(url, content, NowDate, type))
    }

    fun existUrl(url: String): Boolean {
        val db = writableDatabase
        return try {
            val cursor = db.query(TABLE_REQUEST, arrayOf(URL), "$URL=?", arrayOf(url), null, null, null)
            val cnt = cursor.count
            cursor.close()
            cnt > 0
        } catch (ex: Exception) {
            ex.printStackTrace()
            false
        }
    }

    fun findUrl(url: String): NetWorkRequest {
        val db = writableDatabase
        return try {
            val cursor = db.query(TABLE_REQUEST, arrayOf(URL, CONTENT, TIME, TYPE), "$URL=?", arrayOf(url), null, null, null)
            if (cursor.moveToFirst()) {
                val request = getValueFromCursor(cursor)
                cursor.close()
                request
            } else {
                NetWorkRequest.NULL
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            NetWorkRequest.NULL
        } finally {
            db.close()
        }
    }

    private fun getValueFromCursor(cursor: Cursor): NetWorkRequest {
        return try {
            NetWorkRequest(
                    cursor.getString(0),
                    cursor.getString(1),
                    FORMATTER.parse(cursor.getString(2)),
                    NetRequestType.valueOf(cursor.getString(3))
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            NetWorkRequest.NULL
        }
    }

}