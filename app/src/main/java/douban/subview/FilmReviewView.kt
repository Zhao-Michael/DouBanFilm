package douban.subview

import android.content.Context
import douban.DouBanV1
import douban.FilmDetail
import douban.adapter.FilmReviewAdapter
import michaelzhao.R
import util.Rx

class FilmReviewView(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmDetail = filmDetail

    init {
        initRecyclerView()
        initAdapter()
    }

    override fun initAdapter() {
        ShowSwipe()
        Rx.get {
            DouBanV1.getFilmReview(mFilmDetail.id, 1)
        }.set {
            mRecyclerView.adapter = FilmReviewAdapter(mContext, it, this)
            checkEmptyAdapter()
        }.end {
            ShowSwipe(false)
        }
    }

}