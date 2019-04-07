package util

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.iconics.IconicsDrawable
import michaelzhao.App
import michaelzhao.App.Companion.APPNAME
import michaelzhao.BaseActivity
import michaelzhao.R
import org.jetbrains.anko.toast
import java.io.*
import java.util.*
import java.util.regex.Pattern
import java.util.zip.*
import java.lang.reflect.Array.setBoolean
import java.lang.reflect.AccessibleObject.setAccessible
import java.util.concurrent.CountDownLatch


object Util {

    val NowDate get() = Date(System.currentTimeMillis())

    fun Bitmap2Byte(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }

    fun HideKeyBoard(context: Activity) {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(context.window.decorView.windowToken, 0)
    }

    fun CopyToClipBoard(context: Context, str: String) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.primaryClip = ClipData.newPlainText(null, str)
        context.toast("已复制到剪切板")
    }

    fun <T> TimeElapse(str: String, action: () -> T): T {
        val start = System.nanoTime()

        val result = action()

        val elapse = System.nanoTime() - start

        println(str + " : ${elapse / 1000.0 / 1000.0} ms")

        return result
    }

    fun CreateRepeatDrawable(str: String, bgColor: Int, res: Resources): Drawable {
        val txt = str.take(7) + if (str.length > 7) ".." else ""
        val bitmap = Bitmap.createBitmap(55 * (1 + txt.length), 180, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(bgColor)

        val paint = Paint()
        paint.color = Color.WHITE
        paint.alpha = 25
        paint.isAntiAlias = true
        paint.textAlign = Paint.Align.LEFT
        paint.textSize = 40f

        val path = Path()
        path.moveTo(30f, 150f)
        path.lineTo(300f, 0f)
        canvas.drawTextOnPath(txt, path, 0f, 0f, paint)

        val bitmapDrawable = BitmapDrawable(res, bitmap)
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
        return bitmapDrawable
    }

    fun Activity.HideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = Color.TRANSPARENT
                window.navigationBarColor = Color.TRANSPARENT
            }
        }
    }

    fun CreateIcon(context: Context, icon: GoogleMaterial.Icon, size: Int): IconicsDrawable {
        return IconicsDrawable(context).icon(icon).color(BaseActivity.getPrimaryColor()).sizeDp(size)
    }

    fun GetRegexList(source: String, patStr: String, front: String = "", back: String = ""): List<String> {
        val pattern = Pattern.compile(patStr)
        val matcher = pattern.matcher(source)
        val listImg = mutableListOf<String>()
        while (matcher.find()) {
            val thumb = matcher
                    .group()
                    .replace(front, "")
                    .replace(back, "")
                    .trim()
            listImg.add(thumb)
        }
        return listImg
    }

    fun Compress(bs: ByteArray): ByteArray {
        val bos = ByteArrayOutputStream()
        val zos = DeflaterOutputStream(bos)
        zos.write(bs)
        zos.close()
        return bos.toByteArray()
    }

    fun UnCompress(bs: ByteArray): ByteArray {
        val bos = ByteArrayOutputStream()
        val zos = InflaterOutputStream(bos)
        zos.write(bs)
        zos.close()
        return bos.toByteArray()
    }

    fun DisableWarningDialog() {

        try {
            val aClass = Class.forName("android.content.pm.PackageParser\$Package")
            val declaredConstructor = aClass.getDeclaredConstructor(String::class.java)
            declaredConstructor.isAccessible = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            val cls = Class.forName("android.app.ActivityThread")
            val declaredMethod = cls.getDeclaredMethod("currentActivityThread")
            declaredMethod.isAccessible = true
            val activityThread = declaredMethod.invoke(null)
            val mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown")
            mHiddenApiWarningShown.isAccessible = true
            mHiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun GetAPPPath(str: String = ""): String {
        return (Environment.getExternalStorageDirectory().path.toString() + "/$APPNAME/" + str + "/").replace("//", "/")
    }

    fun GetImagesPath(str: String = "", isPath: Boolean = true): String {
        return (GetAPPPath("/Images/") + str + if (isPath) "/" else "")
                .replace("//", "/")
    }

    fun VerifyStoragePermissions(activity: BaseActivity): Boolean {

        if (App.mHasStorgePermission) return true

        val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        val REQUEST_EXTERNAL_STORAGE = 1

        val permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            val lock = CountDownLatch(1)
            var result = false
            activity.PermissionsResultCallBack = { _: Int, _: Array<out String>, grantResults: IntArray ->
                if (grantResults[0] > -1) {
                    result = true
                } else {
                    activity.uiThread(100) {
                        activity.toast("")
                    }
                }
                lock.countDown()
            }
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE)
            lock.await()
            activity.PermissionsResultCallBack = null
            App.mHasStorgePermission = result
            return result
        }
        return true
    }

    fun GetTargetSdkVersion(context: Context): Int {
        val info = context.packageManager.getPackageInfo(context.packageName, 0)
        return info.applicationInfo.targetSdkVersion
    }

}
