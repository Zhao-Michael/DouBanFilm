package douban.subview

import android.content.Context
import android.support.v7.widget.RecyclerView
import douban.DouBanV1
import douban.FilmMan
import douban.adapter.FilmListAdapter
import douban.adapter.IRecyclerViewAdapter
import michaelzhao.R
import util.Rx

class ManWorkView(context: Context, filmMan: FilmMan) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmMan = filmMan
    private var mAdapter: FilmListAdapter? = null
    private var mCurrPageIndex = 0
    private val mStep = 30

    init {
        initRecyclerView()
        initAdapter()
    }


    override fun initAdapter() {
        showSwipe()
        Rx.get {
            DouBanV1.getFilmManWork(mFilmMan.id, mCurrPageIndex, mStep)
        }.set {
            it.works.map { it.subject }
            mAdapter = FilmListAdapter(mContext, it.works.map { it.subject }, this)
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
            DouBanV1.getFilmManWork(mFilmMan.id, mCurrPageIndex, mStep)
        }.set {
            val cnt = mAdapter?.itemCount
            if (cnt != null && it.works.isNotEmpty()) {
                mAdapter?.addWorkList(it.works)
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