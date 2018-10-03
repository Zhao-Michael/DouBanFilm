package douban.subview

import android.content.Context
import douban.FilmDetail
import douban.adapter.FilmCommentAdapter
import michaelzhao.R

class FilmViewComment(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmDetail = filmDetail

    init {
        initRecyclerView()
        initSwipeLayout()
        initSwitchBtn()
    }

    private fun showFilmComment() {
        mRecyclerView.adapter = FilmCommentAdapter(mContext, mFilmDetail)
    }

    override fun onNormalClick() {
        showFilmComment()
    }

    override fun onMoreClick() {
        ShowSwipe()
        HideSwipe()
    }


}