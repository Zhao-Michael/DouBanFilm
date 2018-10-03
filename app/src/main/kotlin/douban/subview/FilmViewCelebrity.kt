package douban.subview

import android.content.Context
import douban.FilmDetail
import douban.adapter.FilmCelebrityAdapter
import michaelzhao.R

class FilmViewCelebrity(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmDetail = filmDetail

    init {
        initRecyclerView()
        initSwipeLayout()
        initSwitchBtn()
        showFilmDetail()
    }

    private fun showFilmDetail() {
        mRecyclerView.adapter = FilmCelebrityAdapter(mContext, mFilmDetail)
    }

    override fun onNormalClick() {
        showFilmDetail()
    }

    override fun onMoreClick() {
        ShowSwipe()
        HideSwipe()
    }

}