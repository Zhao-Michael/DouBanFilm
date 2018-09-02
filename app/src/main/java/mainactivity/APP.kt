package mainactivity

import android.app.Application
import com.orhanobut.hawk.Hawk

class APP : Application() {
    companion object {
        val mCrashHandler = CrashHandler()
        lateinit var Instance: APP
    }

    override fun onCreate() {
        super.onCreate()
        Instance = this
        mCrashHandler.init()
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