package douban.subview

import android.content.Context
import android.support.v7.widget.RecyclerView
import douban.DouBanV2
import douban.adapter.IRecyclerViewAdapter
import douban.adapter.PhotoCommentAdapter
import michaelzhao.R
import util.Rx

class PhotoCommentView(context: Context, id: String) : IFilmView(context) {
    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mID = id

    private var mAdapter: PhotoCommentAdapter? = null
    private var mCurrPageIndex = 0
    private val mStep = 100

    init {
        initRecyclerView()
        initAdapter()
    }

    init {
        initRecyclerView()
        initAdapter()
    }

    override fun initAdapter() {
        showSwipe()
        Rx.get {
            DouBanV2.getFilmPhotoComment(mID, 0)
        }.set {
            mAdapter = PhotoCommentAdapter(mContext, it, this)
            mRecyclerView.adapter = mAdapter
            checkEmptyAdapter()
        }.end {
            showSwipe(false)
        }
    }

    override fun <T : RecyclerView.ViewHolder?> onLoadMore(adapter: IRecyclerViewAdapter<T>) {
        showSwipe()
        Rx.get {
            mCurrPageIndex += mStep
            DouBanV2.getFilmPhotoComment(mID, mCurrPageIndex)
        }.set {
            val cnt = mAdapter?.itemCount
            if (cnt != null && it.isNotEmpty()) {
                mAdapter?.addListComment(it)
                mAdapter?.notifyItemInserted(cnt)
            } else {
                mCurrPageIndex -= mStep
                showNoMoreMsg()
            }
        }.end {
            showSwipe(false)
            adapter.loadMoreFinish()
        }.err {
            showErrMsg(it)
        }
    }


}