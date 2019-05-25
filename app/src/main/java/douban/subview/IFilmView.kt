package douban.subview

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import douban.adapter.IRecyclerViewAdapter
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor
import michaelzhao.BaseActivity
import michaelzhao.R
import org.jetbrains.anko.image
import org.jetbrains.anko.longToast
import util.*
import util.Util.CreateIcon


interface ILoadMore {
    fun <T : RecyclerView.ViewHolder?> onLoadMore(adapter: IRecyclerViewAdapter<T>)
}

abstract class IFilmView(context: Context) : ILoadMore {

    protected val mContext = context
    private var mLoadMore: (() -> Unit)? = null
    protected abstract val mLayout: Int

    protected var mCurrPageIndex = 0
    protected open val mLoadPageStep = 30

    protected val mView: View by lazy { LayoutInflater.from(context).inflate(mLayout, null) }
    protected val mRecyclerView by lazy { mView.find<RecyclerView>(R.id.mRecyclerView) }
    protected val mSwipeLayout by lazy { mView.find<VerSwipeLayout>(R.id.mSwipeLayout) }
    private val mImageNone by lazy { mView.find<ImageView>(R.id.imgae_none) }
    private val mTextNone by lazy { mView.find<TextView>(R.id.text_none) }
    private val mLayoutNone by lazy { mView.find<View>(R.id.layout_none) }

    protected fun initRecyclerView(span: Int = 1) {
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.isNestedScrollingEnabled = false
        mRecyclerView.layoutManager = GridLayoutManager(mContext, span)
        var mInitLoadMore = false
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!mInitLoadMore) {
                    (recyclerView.adapter as? IRecyclerViewAdapter)?.enableLoadMore()
                    mInitLoadMore = true
                }
            }
        })
    }

    open fun initAdapter() {

    }

    fun setLoadMore(loadMore: (() -> Unit)?) {
        mLoadMore = loadMore
    }

    fun <T : RecyclerView.ViewHolder?> loadMore(adapter: IRecyclerViewAdapter<T>) {
        if (mLoadPageStep != 0 && mLoadPageStep - 1 <= adapter.itemCount) {
            onLoadMore(adapter)
            mLoadMore?.invoke()
        }
    }

    override fun <T : RecyclerView.ViewHolder?> onLoadMore(adapter: IRecyclerViewAdapter<T>) {

    }

    fun getView(): View {
        return if (mLayout == 0)
            View(mContext)
        else
            mView
    }

    fun showSwipe(b: Boolean = true) {
        if (mLayout != 0) {
            if (b) mSwipeLayout.Enable() else mSwipeLayout.DisEnable()
            mSwipeLayout.ShowRefresh(b)
        }
    }

    fun checkEmptyAdapter() {
        val bShow = mRecyclerView.adapter?.itemCount == 0
        if (!bShow) {
            mLayoutNone.hide()
        } else {
            mImageNone.image = CreateIcon(mContext, GoogleMaterial.Icon.gmd_sentiment_very_dissatisfied, 40)
            mTextNone.textColor = BaseActivity.getPrimaryColor()
            mLayoutNone.show()
        }
    }

    fun showNoMoreMsg(view: View? = null) {
        if (view != null)
            println(view.toString())
    }

    private fun showSnackBar(view: View?, str: String) {
        if (view != null) {
            val snackbar = Snackbar.make(view, str, Snackbar.LENGTH_LONG)
            snackbar.setAction("关闭") {
                snackbar.dismiss()
            }
            snackbar.setActionTextColor(BaseActivity.getPrimaryColor())
            snackbar.show()
        } else {
            mContext.longToast(str)
        }
    }

    fun showErrMsg(it: Throwable, view: View? = null) {
        if (mLayout != 0)
            showSnackBar(mView, it.message.toString())
        else
            showSnackBar(view, it.message.toString())
    }

}

class FilmView(context: Context) : IFilmView(context) {
    override val mLayout: Int = 0
}