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
class ManPhotoView(context: Context, filmMan: FilmMan) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmMan = filmMan
    private var mCurrPageIndex = 1
    private var mAdapter: FilmPhotoAdapter? = null
    private val mStep = 30

    init {
        initRecyclerView(3)
        initAdapter()
    }

    override fun initAdapter() {
        ShowSwipe()
        Rx.get {
            DouBanV1.getFilmManPhoto(mFilmMan.id, mCurrPageIndex, mStep)
        }.set {
            mAdapter = FilmPhotoAdapter(mRecyclerView, it, this)
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
            DouBanV1.getFilmManPhoto(mFilmMan.id, mCurrPageIndex, mStep)
        }.set {
            val cnt = mAdapter?.itemCount
            if (cnt != null && it.photos.isNotEmpty()) {
                mAdapter?.addListPhotos(it.photos)
                mAdapter?.notifyItemInserted(cnt)
            } else {
                mCurrPageIndex -= mStep
            }
        }.end {
            ShowSwipe(false)
            adapter.LoadMoreFinish()
        }
    }

}