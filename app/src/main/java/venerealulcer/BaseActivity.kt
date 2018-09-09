package venerealulcer

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.orhanobut.hawk.Hawk
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.find

@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity() {

    protected val mToolBar by lazy { find<Toolbar>(R.id.toolbar) }
    protected val mSwipeLayout by lazy { find<SwipeRefreshLayout>(R.id.mSwipeLayout) }
    protected val mRecyclerView by lazy { find<RecyclerView>(R.id.mRecyclerView) }

    abstract val mLayout: Int

    private var primaryColor: Int = 0
    private var accentColor: Int = 0
    private var textColorPrimary: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refreshColor()
        setContentView(mLayout)
        setSupportActionBar(mToolBar)
        setStatusBarColor()
        setNavBarColor()
        setToolBarColor()
    }

    fun setNavBarColor() {
        window.navigationBarColor = getPrimaryColor()
    }

    fun setToolBarColor() {
        mToolBar.backgroundColor = getPrimaryColor()
        mToolBar.setTitleTextColor(getTextColorPrimary())
    }

    fun setStatusBarColor() {
        window.statusBarColor = getPrimaryColor()
    }

    fun refreshColor() {
        primaryColor = Hawk.get(getString(R.string.preference_primary_color),
                getColorValue(R.color.md_indigo_500))
        accentColor = Hawk.get(getString(R.string.preference_accent_color),
                getColorValue(R.color.md_light_blue_500))
        textColorPrimary = Hawk.get(getString(R.string.preference_text_color),
                getColorValue(R.color.accent_white))
    }

    fun getPrimaryColor(): Int {
        return primaryColor
    }

    fun getAccountColor(): Int {
        return accentColor
    }

    fun getTextColorPrimary(): Int {
        return textColorPrimary
    }

    @SuppressLint("SupportAnnotationUsage")
    @ColorInt
    fun setPrimaryColor(id: Int) {
        Hawk.put(getString(R.string.preference_primary_color),
                getColorValue(id))
    }


    @SuppressLint("SupportAnnotationUsage")
    @ColorInt
    fun setAccountColor(id: Int) {
        Hawk.put(getString(R.string.preference_accent_color),
                getColorValue(id))
    }


    fun setToolBarIcon(icon: IIcon) {
        mToolBar.navigationIcon = getDrawableIcon(icon)
    }

    private fun getColorValue(color: Int): Int {
        return ContextCompat.getColor(this, color)
    }

    fun getDrawableIcon(icon: IIcon): IconicsDrawable {
        return IconicsDrawable(this).icon(icon).color(Color.WHITE).sizeDp(18)
    }

}