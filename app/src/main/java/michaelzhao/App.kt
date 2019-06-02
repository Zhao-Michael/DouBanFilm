package michaelzhao

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import com.orhanobut.hawk.Hawk
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso

class App : Application() {
    companion object {
        val mCrashHandler = CrashHandler()
        lateinit var Instance: App
        lateinit var APPNAME: String
        var mHasStorgePermission = false
        const val UserAge = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36"
    }

    override fun onCreate() {
        super.onCreate()
        Instance = this
        APPNAME = "GitFilm"
        initPicasso(this)
        mCrashHandler.init()
        Hawk.init(this).build()
    }

    fun startActivity(cl: Class<out Activity>) {
        val intent = Intent(this, cl)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    class CrashHandler : Thread.UncaughtExceptionHandler {
        override fun uncaughtException(t: Thread?, e: Throwable?) {
            val mes = "Application UncaughtExceptionHandler: " + e?.message
            println(mes)
            System.exit(-1)
        }

        fun init() {
            Thread.setDefaultUncaughtExceptionHandler(this)
        }

    }

    private fun initPicasso(context: Context) {
        val builder = Picasso.Builder(context)
        val cache = LruCache(5 * 1024 * 1024)
        builder.memoryCache(cache)
        val picasso = builder.build()
        Picasso.setSingletonInstance(picasso)
    }


}