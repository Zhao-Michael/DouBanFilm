package douban.subview

import android.content.Context
import android.support.v7.widget.RecyclerView
import douban.DouBanV2
import douban.FilmDetail
import douban.adapter.FilmPhotoAdapter
import douban.adapter.IRecyclerViewAdapter
import michaelzhao.R
import util.Rx

//电影剧照
class FilmPhotoView(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmDetail = filmDetail

    private var mAdapter: FilmPhotoAdapter? = null

    init {
        initRecyclerView(3)
        initAdapter()
    }

    override fun initAdapter() {
        showSwipe()
        Rx.get {
            DouBanV2.getFilmPhoto(mFilmDetail.id, 0)
        }.set {
            mAdapter = FilmPhotoAdapter(mRecyclerView, it, this)
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
            DouBanV2.getFilmPhoto(mFilmDetail.id, mCurrPageIndex)
        }.set {
            val cnt = mAdapter?.itemCount
            if (cnt != null && it.isNotEmpty()) {
                mAdapter?.addListPhotos(it)
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