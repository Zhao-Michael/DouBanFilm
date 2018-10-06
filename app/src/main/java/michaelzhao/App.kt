package michaelzhao

import android.app.Activity
import android.app.Application
import android.content.Intent
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

    fun StartActivity(cl: Class<out Activity>) {
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


}