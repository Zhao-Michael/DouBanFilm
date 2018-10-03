package douban.subview

import android.content.Context
import douban.FilmDetail
import douban.adapter.FilmReviewAdapter
import michaelzhao.R

class FilmViewReview(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmDetail = filmDetail

    init {
        initRecyclerView()
        initSwipeLayout()
        initSwitchBtn()
        showFilmReview()
    }

    private fun showFilmReview() {
        mRecyclerView.adapter = FilmReviewAdapter(mContext, mFilmDetail)
    }

    override fun onNormalClick() {
        showFilmReview()
    }

    override fun onMoreClick() {
        ShowSwipe()
        HideSwipe()
    }

}