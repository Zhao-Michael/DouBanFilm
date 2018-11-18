package douban.subview

import android.content.Context
import android.support.v7.widget.RecyclerView
import douban.DouBanV1
import douban.FilmDetail
import douban.adapter.FilmCommentAdapter
import douban.adapter.IRecyclerViewAdapter
import michaelzhao.R
import util.Rx

class FilmCommentView(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmDetail = filmDetail

    private var mAdapter: FilmCommentAdapter? = null

    init {
        initRecyclerView()
        initAdapter()
    }

    override fun initAdapter() {
        showSwipe()
        Rx.get {
            DouBanV1.getFilmComment(mFilmDetail.id, 0, mLoadPageStep)
        }.set {
            mAdapter = FilmCommentAdapter(mContext, it, this)
            mRecyclerView.adapter = mAdapter
            checkEmptyAdapter()
        }.end {
            showSwipe(false)
        }
    }

    override fun <T : RecyclerView.ViewHolder?> onLoadMore(adapter: IRecyclerViewAdapter<T>) {
        showSwipe()
        Rx.get {
            mCurrPageIndex += mLoadPageStep
            DouBanV1.getFilmComment(mFilmDetail.id, mCurrPageIndex, mLoadPageStep)
        }.set {
            val cnt = mAdapter?.itemCount
            if (cnt != null && it.comments.isNotEmpty()) {
                mAdapter?.addListComment(it.comments)
                mAdapter?.notifyItemInserted(cnt)
            } else {
                mCurrPageIndex -= mLoadPageStep
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