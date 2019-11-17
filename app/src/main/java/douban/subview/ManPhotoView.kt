package douban.subview

import android.content.Context
import android.support.v7.widget.RecyclerView
import douban.DouBanV1
import douban.FilmMan
import douban.adapter.FilmPhotoAdapter
import douban.adapter.IRecyclerViewAdapter
import michaelzhao.R
import util.Rx

//影人图片
class ManPhotoView(context: Context, filmMan: DouBanV1.CelebrityDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmMan = filmMan
    private var mAdapter: FilmPhotoAdapter? = null

    init {
        initRecyclerView(3)
        initAdapter()
    }

    override fun initAdapter() {
        showSwipe()
        Rx.get {
            DouBanV1.getFilmManPhoto(mFilmMan.id, mCurrPageIndex, mLoadPageStep)
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
            DouBanV1.getFilmManPhoto(mFilmMan.id, mCurrPageIndex, mLoadPageStep)
        }.set {
            val cnt = mAdapter?.itemCount
            if (cnt != null && it.photos.isNotEmpty()) {
                mAdapter?.addListPhotos(it.photos)
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