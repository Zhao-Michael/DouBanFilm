package douban.adapter

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import douban.subview.IFilmView
import michaelzhao.BaseActivity
import util.dip2px

abstract class IRecyclerViewAdapter<T : RecyclerView.ViewHolder?>(filmView: IFilmView) : RecyclerView.Adapter<T>() {

    private var mMargin: Int? = null
    protected var mImageWidth: Int? = null
    private val mIFilmView = filmView
    private var mLoadingMore = false

    override fun onBindViewHolder(holder: T, position: Int) {
        if (holder != null) {
            val position = holder.adapterPosition
            if (position == 0) {
                val lp = holder.itemView?.layoutParams as? ViewGroup.MarginLayoutParams
                if (lp != null) {
                    if (mMargin == null) mMargin = lp.topMargin * 2
                    lp.topMargin = mMargin!!
                }
            } else if (position == itemCount - 1) {
                val lp = holder.itemView?.layoutParams as? ViewGroup.MarginLayoutParams
                if (lp != null) {
                    if (mMargin == null) mMargin = lp.topMargin * 2
                    lp.bottomMargin = mMargin!!
                }
            }
        }
    }

    protected fun setImageHeight(recycler: RecyclerView, offsetWid: Int = 0, spCount: Int? = null) {
        val sc = spCount ?: (recycler.layoutManager as? GridLayoutManager)?.spanCount
        if (sc != null) {
            mImageWidth = BaseActivity.getScreenSize().x / sc - offsetWid.dip2px() - 1
        }
    }

    protected fun checkToEnd(pos: Int) {
        if (mLoadingMore) return
        mIFilmView.LoadMore(pos == itemCount - 1, this)
    }

    fun LoadMoreFinish() {
        mLoadingMore = false
    }

}