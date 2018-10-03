package douban.subview

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.textColor
import util.OnClick
import util.VerticalSwipeRefreshLayout
import michaelzhao.BaseActivity
import michaelzhao.R

abstract class IFilmView(context: Context) {

    protected val mContext = context

    protected abstract val mLayout: Int

    protected val mView: View by lazy { LayoutInflater.from(context).inflate(mLayout, null) }
    protected val mRecyclerView by lazy { mView.find<RecyclerView>(R.id.mRecyclerView) }
    protected val mSwipeLayout by lazy { mView.find<VerticalSwipeRefreshLayout>(R.id.mSwipeLayout) }

    private val mTextNormal by lazy { mView.find<TextView>(R.id.text_normal) }
    private val mTextMore by lazy { mView.find<TextView>(R.id.text_more) }

    private val mGrayColor by lazy { ContextCompat.getColor(mContext, R.color.dark_gray) }
    private var mIsNormalMode = false

    protected fun initRecyclerView(span: Int = 1) {
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.scrollBarStyle
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

        switchState(true)
        mTextNormal.OnClick { switchState(true) }
        mTextMore.OnClick { switchState(false) }
    }

    private fun switchState(isNormal: Boolean) {
        mSwipeLayout.isEnabled = !isNormal
        if (isNormal == mIsNormalMode) return
        mIsNormalMode = isNormal
        if (isNormal) {
            mTextNormal.textColor = BaseActivity.getPrimaryColor()
            mTextMore.textColor = mGrayColor
            onNormalClick()
        } else {
            mTextNormal.textColor = mGrayColor
            mTextMore.textColor = BaseActivity.getPrimaryColor()
            onMoreClick()
        }
    }

    open fun onNormalClick() {

    }

    open fun onMoreClick() {

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

}

class FilmView(context: Context) : IFilmView(context) {
    override val mLayout: Int = 0
}