package database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.gson.Gson
import douban.FilmDetail
import michaelzhao.App
import util.Util
import util.Util.NowDate
import util.fromJson

class FavoriteDB : SQLiteOpenHelper(App.Instance, DATABASE_FILENAME, null, 1) {

    companion object {
        private const val TYPE = "type"
        private const val CONTENT = "content"
        private const val TAG = "tag"  //for search
        private const val TIME = "time"

        private const val DATABASE_FILENAME = "GitFilmFavoriteDB.db"
        private const val TABLE_REQUEST = "FavoriteDB"
        private const val CREATE_FAVORITE_DATABASE: String = "create table $TABLE_REQUEST (" +
                "id integer primary key autoincrement, " +
                "$TYPE text, " +
                "$CONTENT text, " +
                "$TAG text, " +
                "$TIME text )"

        val Instance by lazy { FavoriteDB() }
    }

    private val mLock = Any()

    override fun onCreate(db: SQLiteDatabase?) {
        synchronized(mLock) {
            try {
                db?.execSQL(CREATE_FAVORITE_DATABASE)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) = Unit

    fun addFilmDetail(film: FilmDetail): Boolean {
        synchronized(mLock) {
            val db = writableDatabase
            return try {
                val content = Util.Compress(Gson().toJson(film).toByteArray())
                val cv = createCValue(FavoriteType.Film, content, film.id)
                if (existFilmDetail(film.id)) {
                    db.update(TABLE_REQUEST, cv, "$TAG=?", arrayOf(film.id))
                    println("DateBase[$TABLE_REQUEST] : update ${film.title} into favorite database")
                } else {
                    db.insert(TABLE_REQUEST, null, cv)
                    println("DateBase[$TABLE_REQUEST] : insert ${film.title} into favorite database")
                }
                true
            } catch (ex: Exception) {
                ex.printStackTrace()
                false
            } finally {
                db.close()
            }
        }
    }

    fun removeFilmDetail(film: FilmDetail): Boolean {
        synchronized(mLock) {
            val db = writableDatabase
            return try {
                if (existFilmDetail(film.id)) {
                    db.delete(TABLE_REQUEST, "$TAG=? and $TYPE=?", arrayOf(film.id, FavoriteType.Film.toString()))
                }
                true
            } catch (ex: Exception) {
                ex.printStackTrace()
                false
            } finally {
                db.close()
            }
        }
    }

    fun getAllFilmDetail(): List<FilmDetail> {
        val lstResult = mutableListOf<FilmDetail>()
        synchronized(mLock) {
            val db = writableDatabase
            return try {
                val cursor = db.query(TABLE_REQUEST, null, null, null, null, null, null)
                if (cursor.count > 0) {
                    cursor.moveToFirst()
                    val index = cursor.getColumnIndex(CONTENT)
                    do {
                        try {
                            lstResult.add(Gson().fromJson(String(Util.UnCompress(cursor.getBlob(index)))))
                        } catch (e: Exception) {
                        }
                    } while (cursor.moveToNext())
                }
                cursor.close()
                lstResult
            } catch (ex: Exception) {
                ex.printStackTrace()
                lstResult
            }
        }
    }


    fun existFilmDetail(id: String): Boolean {
        synchronized(mLock) {
            val db = writableDatabase
            return try {
                val cursor = db.query(TABLE_REQUEST, arrayOf(TYPE, CONTENT, TAG, TIME), "$TAG=?", arrayOf(id), null, null, null)
                val cnt = cursor.count
                cursor.close()
                cnt > 0
            } catch (ex: Exception) {
                ex.printStackTrace()
                false
            }
        }
    }

    private fun createCValue(type: FavoriteType, content: ByteArray, tag: String): ContentValues {
        val cv = ContentValues()
        cv.put(TYPE, type.name)
        cv.put(CONTENT, content)
        cv.put(TAG, tag)
        cv.put(TIME, DBConstUtil.FORMATTER.format(NowDate))
        return cv
    }

}