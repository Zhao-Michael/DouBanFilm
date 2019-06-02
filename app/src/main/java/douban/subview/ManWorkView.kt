package douban.subview

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
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

    init {
        initRecyclerView()
        initAdapter()
    }


    override fun initAdapter() {
        showSwipe()
        Rx.get {
            DouBanV1.getFilmManWork(mFilmMan.id, mCurrPageIndex, mLoadPageStep)
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
            mCurrPageIndex += mLoadPageStep
            DouBanV1.getFilmManWork(mFilmMan.id, mCurrPageIndex, mLoadPageStep)
        }.set {
            val cnt = mAdapter?.itemCount
            if (cnt != null && it.works.isNotEmpty()) {
                mAdapter?.addWorkList(it.works)
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