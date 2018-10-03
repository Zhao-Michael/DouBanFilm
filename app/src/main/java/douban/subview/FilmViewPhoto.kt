package douban.subview

import android.content.Context
import douban.FilmDetail
import douban.adapter.FilmPhotosAdapter
import michaelzhao.R

class FilmViewPhoto(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmDetail = filmDetail


    init {
        initRecyclerView(3)
        initSwipeLayout()
        initSwitchBtn()
    }

    private fun showFromFilmDetail() {
        mRecyclerView.adapter = FilmPhotosAdapter(mContext, mFilmDetail)
    }

    override fun onNormalClick() {
        showFromFilmDetail()
    }

    override fun onMoreClick() {
        ShowSwipe()
        HideSwipe()
    }

}