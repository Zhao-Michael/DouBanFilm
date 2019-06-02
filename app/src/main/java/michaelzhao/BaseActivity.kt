package michaelzhao

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.orhanobut.hawk.Hawk
import org.jetbrains.anko.*
import util.VerSwipeLayout
import util.*
import util.Util.CreateIcon
import util.Util.VerifyStoragePermissions

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
    protected val mSwipeLayout by lazy { find<VerSwipeLayout>(R.id.mSwipeLayout) }
    var PermissionsResultCallBack: ((requestCode: Int, permissions: Array<out String>, grantResults: IntArray) -> Unit)? = null
    abstract val mLayout: Int

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionsResultCallBack?.invoke(requestCode, permissions, grantResults)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refreshColor()
        setContentView(mLayout)
        setSupportActionBar(mToolBar)
        setStatusBarColor()
        setNavBarColor()
        setToolBarColor()
        initScreenSize()
        doAsync { VerifyStoragePermissions(this@BaseActivity) }
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
                getColorValue(R.color.md_deep_orange_400))
        accentColor = Hawk.get(getString(R.string.preference_accent_color),
                getColorValue(R.color.md_light_blue_500))
        textColorPrimary = Hawk.get(getString(R.string.preference_text_color),
                getColorValue(R.color.accent_white))
    }

    @SuppressLint("SupportAnnotationUsage")
    fun setPrimaryColor(id: Int) {
        Hawk.put(getString(R.string.preference_primary_color),
                getColorValue(id))
    }

    @SuppressLint("SupportAnnotationUsage")
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

    fun getDrawableIcon(icon: IIcon, size: Int = 18): IconicsDrawable {
        return IconicsDrawable(this).icon(icon).color(Color.WHITE).sizeDp(size)
    }

    //init Find Nothing Layout
    protected fun initNoneLayout() {
        val mImageNone = find<ImageView>(R.id.imgae_none)
        val mTextNone = find<TextView>(R.id.text_none)
        mImageNone.image = CreateIcon(this, GoogleMaterial.Icon.gmd_sentiment_very_dissatisfied, 40)
        mTextNone.textColor = BaseActivity.getPrimaryColor()
    }

    override fun onPause() {
        super.onPause()
        Util.HideKeyBoard(this)
    }

}