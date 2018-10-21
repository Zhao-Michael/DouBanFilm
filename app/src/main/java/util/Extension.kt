@file:Suppress("DEPRECATION")

package util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Looper
import android.support.design.widget.TabLayout
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.Gson
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.orhanobut.hawk.Hawk
import com.squareup.picasso.Picasso
import douban.adapter.FilmBriefAdapter
import douban.adapter.FilmListAdapter
import douban.FilmList
import douban.SearchBrief
import org.apache.commons.lang3.StringEscapeUtils
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.image
import org.jetbrains.anko.uiThread
import michaelzhao.App
import java.lang.reflect.Field
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
    if (Looper.getMainLooper().thread == Thread.currentThread()) {
        run()
    } else {
        doAsync {
            uiThread {
                run()
            }
        }
    }
}

fun Any.uiThread(delay: Long, run: () -> Unit) {
    doAsync {
        Thread.sleep(delay)
        uiThread { run() }
    }
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

var RecyclerView.FilmAdapter: FilmList
    get() {
        return (adapter as FilmListAdapter).getFilmList()
    }
    set(it) {
        uiThread { adapter = FilmListAdapter(context, it) }
    }

var RecyclerView.BriefAdapter: Array<SearchBrief>
    get() {
        return (adapter as FilmBriefAdapter).getListBrief()
    }
    set(it) {
        uiThread { adapter = FilmBriefAdapter(it, context) }
    }


fun TabLayout.setTabStyle(dstDip: Int = 10) {
    var tabStrip: Field? = null
    try {
        tabStrip = javaClass.getDeclaredField("mTabStrip")
    } catch (e: NoSuchFieldException) {
        e.printStackTrace()
    }

    if (tabStrip != null) {
        tabStrip.isAccessible = true
        var llTab: LinearLayout? = null
        try {
            llTab = tabStrip.get(this) as LinearLayout?
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        if (llTab != null) {
            val dst = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dstDip.toFloat(), Resources.getSystem().displayMetrics).toInt()
            for (i in 0 until llTab.childCount) {
                val child = llTab.getChildAt(i)
                child.setPadding(0, 0, 0, 0)
                val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
                params.leftMargin = dst
                params.rightMargin = dst
                child.layoutParams = params
                child.invalidate()
            }
        }
    }

    fun getTextView(pos: Int): TextView? {
        try {
            val layout = getChildAt(0) as? LinearLayout
            if (layout != null) {
                val layout1 = layout.getChildAt(pos) as? LinearLayout
                if (layout1 != null) {
                    return layout1.getChildAt(1) as? TextView
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    getTextView(selectedTabPosition)?.paint?.isFakeBoldText = true

    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) = Unit
        override fun onTabUnselected(tab: TabLayout.Tab?) {
            if (tab != null) {
                val text = getTextView(tab.position)
                text?.paint?.isFakeBoldText = false
            }
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            if (tab != null) {
                val text = getTextView(tab.position)
                text?.paint?.isFakeBoldText = true
            }
        }
    })

}

fun ImageView.setImageUrl(url: String, holder: Int = 0) {
    Picasso.get()
            .load(url)
            .apply {
                if (holder != 0)
                    placeholder(holder)
            }
            .into(this)
}

fun String.ReplaceEmpty(): String {
    return if (isBlank())
        "无"
    else
        this.trim()
}

fun ImageView.setIcon(icon: IIcon) {
    image = IconicsDrawable(context).icon(icon).color(Color.WHITE).sizeDp(18)
}

fun ImageView.setIcon(icon: IIcon, color: Int, size: Int) {
    image = IconicsDrawable(context).icon(icon).color(color).sizeDp(size)
}

fun View.SetHeight(height: Int) {
    val layoutParams = this.layoutParams
    layoutParams.height = height
    this.layoutParams = layoutParams
}

fun View.SetWidth(height: Int) {
    val layoutParams = this.layoutParams
    layoutParams.width = height
    this.layoutParams = layoutParams
}

fun <T> HawkPut(id: Int, value: T) {
    Hawk.put<T>(App.Instance.getString(id), value)
}

fun <T> HawkGet(id: Int): T {
    return Hawk.get<T>(App.Instance.getString(id))
}

fun <T> HawkGet(id: Int, defaultValue: T): T {
    return Hawk.get<T>(App.Instance.getString(id), defaultValue)
}