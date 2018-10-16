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

    protected fun initRecyclerView(span: Int = 1) {
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.isNestedScrollingEnabled = false
        mRecyclerView.layoutManager = GridLayoutManager(mContext, span)
    }

    open fun initAdapter() {

    }

    open fun onLoadMore() {

    }

    fun getView(): View {
        return if (mLayout == 0)
            View(mContext)
        else
            mView
    }

    fun ShowSwipe(b: Boolean = true) {
        if (mLayout != 0) {
            if (b) mSwipeLayout.Enable() else mSwipeLayout.DisEnable()
            mSwipeLayout.ShowRefresh(b)
        }
    }
}

class FilmView(context: Context) : IFilmView(context) {
    override val mLayout: Int = 0
}