package util

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.iconics.IconicsDrawable
import michaelzhao.BaseActivity
import org.jetbrains.anko.toast
import java.io.*
import java.util.*
import java.util.regex.Pattern
import java.util.zip.*


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

}
