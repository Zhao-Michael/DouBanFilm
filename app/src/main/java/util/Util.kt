package util

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.inputmethod.InputMethodManager
import java.io.ByteArrayOutputStream
import android.graphics.Typeface


fun Bitmap2Byte(bitmap: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return stream.toByteArray()
}

fun HideKeyBoard(context: Activity) {
    val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(context.window.decorView.windowToken, 0)
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
    paint.alpha = 20
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