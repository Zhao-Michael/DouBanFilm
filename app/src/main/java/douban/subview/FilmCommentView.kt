package douban.subview

import android.content.Context
import android.support.v7.widget.RecyclerView
import douban.DouBanV1
import douban.FilmDetail
import douban.adapter.FilmCommentAdapter
import douban.adapter.FilmListAdapter
import douban.adapter.IRecyclerViewAdapter
import michaelzhao.R
import util.Rx

class FilmCommentView(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmDetail = filmDetail

    private var mAdapter: FilmCommentAdapter? = null
    private var mCurrPageIndex = 0
    private val mStep = 30

    init {
        initRecyclerView()
        initAdapter()
    }

    override fun initAdapter() {
        ShowSwipe()
        Rx.get {
            DouBanV1.getFilmComment(mFilmDetail.id, 0, mStep)
        }.set {
            mAdapter = FilmCommentAdapter(mContext, it, this)
            mRecyclerView.adapter = mAdapter
            checkEmptyAdapter()
        }.end {
            ShowSwipe(false)
        }
    }

    override fun <T : RecyclerView.ViewHolder?> onLoadMore(adapter: IRecyclerViewAdapter<T>) {
        ShowSwipe()
        Rx.get {
            mCurrPageIndex += mStep
            DouBanV1.getFilmComment(mFilmDetail.id, mCurrPageIndex, mStep)
        }.set {
            val cnt = mAdapter?.itemCount
            if (cnt != null && it.comments.isNotEmpty()) {
                mAdapter?.addListComment(it.comments)
                mAdapter?.notifyItemInserted(cnt)
            } else {
                mCurrPageIndex -= mStep
                showNoMoreMsg()
            }
        }.end {
            ShowSwipe(false)
            adapter.LoadMoreFinish()
        }.err {
            showErrMsg(it)
        }
    }

}