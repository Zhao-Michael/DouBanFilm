package douban.subview

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.textColor
import util.OnClick
import util.VerticalSwipeRefreshLayout
import michaelzhao.BaseActivity
import michaelzhao.R
import org.jetbrains.anko.image
import util.Hide
import util.Util.CreateIcon
import util.uiThread
import kotlin.math.max
import kotlin.math.min

abstract class IFilmView(context: Context) {

    protected val mContext = context

    protected abstract val mLayout: Int

    protected var mItemCount = 30

    protected val mView: View by lazy { LayoutInflater.from(context).inflate(mLayout, null) }
    protected val mRecyclerView by lazy { mView.find<RecyclerView>(R.id.mRecyclerView) }
    protected val mSwipeLayout by lazy { mView.find<VerticalSwipeRefreshLayout>(R.id.mSwipeLayout) }

    private val mLayoutHeader by lazy { mView.find<RelativeLayout>(R.id.header_layout) }

    //Normal and More Layout
    private val mLayoutSwitcher by lazy { mView.find<LinearLayout>(R.id.switcher_layout) }
    private val mTextNormal by lazy { mView.find<TextView>(R.id.text_normal) }
    private val mTextMore by lazy { mView.find<TextView>(R.id.text_more) }
    private val mImageDirection by lazy { mView.find<ImageView>(R.id.image_direction) }

    //Page Layout
    private val mLayoutPage by lazy { mView.find<LinearLayout>(R.id.page_layout) }
    private val mImagePrevious by lazy { mView.find<ImageView>(R.id.image_previous) }
    private val mImageNext by lazy { mView.find<ImageView>(R.id.image_next) }
    private val mTextCurrPage by lazy { mView.find<TextView>(R.id.text_curr_pages) }
    private val mTextAllPage by lazy { mView.find<TextView>(R.id.text_total_pages) }

    protected var currPage: Int
        get() = mTextCurrPage.text.toString().toInt()
        set(value) {
            mTextCurrPage.text = min(max(value, 1), allPage).toString()
        }

    private var allPage: Int
        get() = mTextAllPage.text.toString().toInt()
        set(value) {
            mTextAllPage.text = max(value, currPage).toString()
        }

    private val mGrayColor by lazy { ContextCompat.getColor(mContext, R.color.dark_gray) }
    private var mIsNormalMode = false

    protected fun initRecyclerView(span: Int = 1) {
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.isNestedScrollingEnabled = false
        mRecyclerView.layoutManager = GridLayoutManager(mContext, span)
    }

    protected fun initSwipeLayout() {
        mSwipeLayout.onRefresh {
            if (!mIsNormalMode)
                onMoreClick()
        }
    }

    protected fun initSwitchBtn() {
        if (mLayout != R.layout.film_common_subview_layout)
            throw Exception("mLayout must be film_common_subview_layout")
        mImageDirection.image = CreateIcon(mContext, GoogleMaterial.Icon.gmd_keyboard_arrow_left, 8)
        switchState(true)
        mLayoutSwitcher.tag = true
        mLayoutSwitcher.OnClick {
            val flag = mLayoutSwitcher.tag as Boolean
            mLayoutSwitcher.tag = !flag
            switchState(!flag)
        }
    }

    private fun switchState(isNormal: Boolean) {
        mSwipeLayout.isEnabled = !isNormal
        if (isNormal == mIsNormalMode) return
        mIsNormalMode = isNormal
        if (isNormal) {//Switch to Normal
            mTextNormal.textColor = BaseActivity.getPrimaryColor()
            mTextMore.textColor = mGrayColor
            mImageDirection.image = CreateIcon(mContext, GoogleMaterial.Icon.gmd_keyboard_arrow_left, 8)
            mLayoutPage.Hide()
            onNormalClick()
        } else {
            mTextNormal.textColor = mGrayColor
            mImageDirection.image = CreateIcon(mContext, GoogleMaterial.Icon.gmd_keyboard_arrow_right, 8)
            mTextMore.textColor = BaseActivity.getPrimaryColor()
            onMoreClick()
        }
    }

    open fun onNormalClick() {

    }

    open fun onMoreClick() {

    }

    protected fun initPageSwitch() {
        mImagePrevious.image = CreateIcon(mContext, GoogleMaterial.Icon.gmd_navigate_before, 12)
        mImagePrevious.OnClick {
            if (currPage == 1) return@OnClick
            currPage--
            onSwitchPage(currPage)
        }
        mTextCurrPage.OnClick { mImagePrevious.callOnClick() }
        mImageNext.image = CreateIcon(mContext, GoogleMaterial.Icon.gmd_navigate_next, 12)
        mImageNext.OnClick {
            if (allPage == currPage) return@OnClick
            currPage++
            onSwitchPage(currPage)
        }
        mTextAllPage.OnClick { mImageNext.callOnClick() }
    }

    protected fun setTotalPage(page: Int) {
        uiThread { allPage = page }
    }

    open fun onSwitchPage(page: Int) {

    }


    fun getView(): View {
        return if (mLayout == 0)
            View(mContext)
        else
            mView
    }

    fun ShowSwipe(b: Boolean = true) {
        if (mLayout != 0) {
            mSwipeLayout.ShowRefresh(b)
        }
    }

    fun HideSwipe() {
        ShowSwipe(false)
    }

    fun DisabelSwipe() {
        if (mLayout != 0) {
            mSwipeLayout.isEnabled = false
        }
    }

    fun ShowHeaderLayout(b: Boolean = true) {
        mLayoutHeader.visibility = if (b) View.VISIBLE else View.GONE
    }

    fun ShowSwitcherLayout(b: Boolean = true) {
        mLayoutSwitcher.visibility = if (b) View.VISIBLE else View.GONE
    }

    fun ShowPageSwitch(b: Boolean = true) {
        mLayoutPage.visibility = if (b) View.VISIBLE else View.GONE
    }

    fun EnablePageSwitch(b: Boolean = true) {
        mLayoutPage.isEnabled = b
    }

}

class FilmView(context: Context) : IFilmView(context) {
    override val mLayout: Int = 0
}