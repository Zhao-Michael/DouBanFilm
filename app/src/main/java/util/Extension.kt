@file:Suppress("DEPRECATION")

package util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.annotation.Nullable
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.util.Base64
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.apache.commons.lang3.StringEscapeUtils
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import venerealulcer.App
import java.net.URLDecoder
import java.net.URLEncoder

inline fun <reified T : Any> Gson.fromJson(json: String): T {
    return Gson().fromJson(json, T::class.java)
}

fun String.fromURL(): String {
    return URLDecoder.decode(this, "utf-8")
}

fun String.toURL(): String {
    return URLEncoder.encode(this, "utf-8")
}

fun String.fromBase64(): String {
    return String(Base64.decode(this.replace("_", "/").toByteArray(), Base64.DEFAULT))
}

fun String.toBase64(): String {
    return String(Base64.encode(this.toByteArray(), Base64.DEFAULT))
            .replace("/", "_")
            .replace("\n", "")
            .replace("\r", "")
}

fun String.toUnicode(): String {
    return StringEscapeUtils.unescapeJava(this)
}

fun String.fromUnicode(): String {
    return StringEscapeUtils.escapeJava(this)
}

fun ViewGroup.MarginLayoutParams.SetMargins(left: Int, top: Int, right: Int, bottom: Int) {
    setMargins(left.dip2px(), top.dip2px(), right.dip2px(), bottom.dip2px())
}

fun View.SetPadding(left: Int, top: Int, right: Int, bottom: Int) {
    setPadding(left.dip2px(), top.dip2px(), right.dip2px(), bottom.dip2px())
}

fun Any.uiThread(run: () -> Unit) {
    doAsync {
        uiThread {
            run()
        }
    }
}

fun Any.uiThread(delay: Long, run: () -> Unit) {
    doAsync {
        Thread.sleep(delay)
        uiThread { run() }
    }
}

fun SwipeRefreshLayout.ShowRefresh(): SwipeRefreshLayout {
    uiThread { isRefreshing = true }
    return this
}

fun SwipeRefreshLayout.HideRefresh(): SwipeRefreshLayout {
    uiThread(300) { isRefreshing = false }
    return this
}

fun ViewGroup.Enable() {
    uiThread { isEnabled = true }
}

fun ViewGroup.DisEnable() {
    uiThread { isEnabled = false }
}

fun View.Show(): View {
    uiThread { visibility = VISIBLE }
    return this
}

fun View.Hide(): View {
    uiThread { visibility = GONE }
    return this
}


fun Activity.GetText(id: Int): String {
    return getText(id).toString()
}


fun MenuItem.OnItemClick(action: () -> Unit): MenuItem {
    setOnMenuItemClickListener {
        action.invoke()
        return@setOnMenuItemClickListener true
    }
    return this
}

fun <T> MutableList<out T>.removeLast() {
    if (size > 1) removeAt(size - 1)
}

fun View.OnClick(action: () -> Unit) {
    setOnClickListener { action.invoke() }
}

fun View.OnLongClick(action: () -> Unit) {
    setOnLongClickListener {
        action.invoke()
        return@setOnLongClickListener true
    }
}

fun View.IsShown(): Boolean {
    return visibility == VISIBLE
}

fun Context.GetHeightInPx(): Float {
    return this.resources.displayMetrics.heightPixels.toFloat()
}

fun Context.GetWidthInPx(): Float {
    return this.resources.displayMetrics.widthPixels.toFloat()
}

fun Int.dip2px(): Int {
    val scale = App.Instance.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun Context.inflate(id: Int, viewGroup: ViewGroup): View {
    return LayoutInflater.from(this).inflate(id, viewGroup, false)
}

//fun <T> Observable<T>.set(action: (T) -> Unit): Rx<T> {
//    val rx = Rx.createRx(Consumer(action))
//    subscribe(rx)
//    return rx
//}


class Rx<T> private constructor() : Observer<T> {

    companion object {
        fun <T> get(action: () -> T): Rx<T> {
            val obs = Observable.create<T> {
                val t = action()
                it.onNext(t)
                it.onComplete()
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            return Rx<T>(obs)
        }
    }

    private constructor(_observer: Observable<T>) : this() {
        mObserver = _observer
        mObserver.subscribe(this)
    }

    lateinit var mObserver: Observable<T>
    private var onNext = Consumer<T> {}
    private var onError = Consumer<Throwable> {}
    private var onComplete = Action {}
    private var onSubscribe = Consumer<Disposable> {}

    fun err(action: (Throwable) -> Unit) {
        onError = Consumer(action)
    }

    fun set(action: (T) -> Unit): Rx<T> {
        onNext = Consumer(action)
        return this
    }

    override fun onComplete() {
        onComplete.run()
    }

    override fun onSubscribe(d: Disposable) {
        onSubscribe.accept(d)
    }

    override fun onNext(t: T) {
        onNext.accept(t)
    }

    override fun onError(e: Throwable) {
        onError.accept(e)
    }


}