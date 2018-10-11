package michaelzhao

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.orhanobut.hawk.Hawk
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.find
import util.VerticalSwipeRefreshLayout
import util.*

@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity() {

    companion object {
        private var primaryColor: Int = 0
        private var accentColor: Int = 0
        private var textColorPrimary: Int = 0
        private var ScreenSize = Point(0, 0)
        fun getPrimaryColor(): Int {
            return primaryColor
        }

        private fun getAccountColor(): Int {
            return accentColor
        }

        fun getTextColorPrimary(): Int {
            return textColorPrimary
        }

        fun getScreenSize() = ScreenSize
    }

    protected val mToolBar by lazy { find<Toolbar>(R.id.toolbar) }
    protected val mSwipeLayout by lazy { find<VerticalSwipeRefreshLayout>(R.id.mSwipeLayout) }

    abstract val mLayout: Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refreshColor()
        setContentView(mLayout)
        setSupportActionBar(mToolBar)
        setStatusBarColor()
        setNavBarColor()
        setToolBarColor()
        initScreenSize()
    }

    private fun initScreenSize() {
        if (ScreenSize.x == 0) {
            val wid = HawkGet(R.string.preference_screen_width, 0)
            if (wid == 0) {
                windowManager.defaultDisplay.getSize(ScreenSize)
                HawkPut(R.string.preference_screen_width, ScreenSize.x)
                HawkPut(R.string.preference_screen_height, ScreenSize.y)
            } else {
                ScreenSize.x = HawkGet(R.string.preference_screen_width)
                ScreenSize.y = HawkGet(R.string.preference_screen_height)
            }
        }
    }

    fun setNavBarColor() {
        window.navigationBarColor = getPrimaryColor()
    }

    fun setToolBarColor() {
        mToolBar.backgroundColor = getPrimaryColor()
        mToolBar.setTitleTextColor(getTextColorPrimary())
        setToolBarTitle()
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

    @SuppressLint("SupportAnnotationUsage")
    @ColorInt
    fun setPrimaryColor(id: Int) {
        Hawk.put(getString(R.string.preference_primary_color),
                getColorValue(id))
    }

    @SuppressLint("SupportAnnotationUsage")
    @ColorInt
    private fun setAccountColor(id: Int) {
        Hawk.put(getString(R.string.preference_accent_color),
                getColorValue(id))
    }

    fun setToolBarTitle(title: String = "") {
        supportActionBar?.title = title
    }

    fun setToolBarTitle(titleID: Int) {
        mToolBar.title = getString(titleID)
    }

    fun setToolBarIcon(icon: IIcon) {
        mToolBar.navigationIcon = getDrawableIcon(icon)
    }

    protected fun getColorValue(color: Int): Int {
        return ContextCompat.getColor(this, color)
    }

    fun getDrawableIcon(icon: IIcon): IconicsDrawable {
        return IconicsDrawable(this).icon(icon).color(Color.WHITE).sizeDp(18)
    }

}