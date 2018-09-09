package venerealulcer

import android.app.Application
import com.orhanobut.hawk.Hawk

class App : Application() {
    companion object {
        val mCrashHandler = CrashHandler()
        lateinit var Instance: App
    }

    override fun onCreate() {
        super.onCreate()
        Instance = this
        //mCrashHandler.init()
        Hawk.init(this).build()
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

}