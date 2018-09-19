package util

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun Bitmap2Byte(bitmap: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return stream.toByteArray()
}
