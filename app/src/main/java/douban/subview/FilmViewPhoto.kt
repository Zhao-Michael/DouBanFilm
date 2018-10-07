package douban.subview

import android.content.Context
import douban.DouBanV1
import douban.FilmDetail
import douban.FilmPhoto
import douban.adapter.FilmPhotosAdapter
import michaelzhao.R
import util.Rx

class FilmViewPhoto(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmDetail = filmDetail
    private var mFilmPhoto: FilmPhoto? = null

    init {
        initRecyclerView(3)
        initSwipeLayout()
        initSwitchBtn()
    }

    override fun onNormalClick() {
        mRecyclerView.adapter = FilmPhotosAdapter(mContext, mFilmDetail)
    }

    override fun onMoreClick() {
        if (mFilmPhoto == null) {
            Rx.get {
                ShowSwipe()
                DouBanV1.getFilmPhoto(mFilmDetail.id)
            }.set {
                mFilmPhoto = it
                mRecyclerView.adapter = FilmPhotosAdapter(mContext, mFilmPhoto!!)
            }.com {
                HideSwipe()
            }
        } else {
            mRecyclerView.adapter = FilmPhotosAdapter(mContext, mFilmPhoto!!)
            HideSwipe()
        }
    }

}