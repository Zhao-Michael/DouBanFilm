package douban.subview

import android.content.Context
import douban.DouBanV1
import douban.FilmDetail
import douban.adapter.FilmCommentAdapter
import michaelzhao.R
import util.Rx

class FilmCommentView(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmDetail = filmDetail

    init {
        initRecyclerView()
        initAdapter()
    }

    override fun initAdapter() {
        ShowSwipe()
        Rx.get {
            DouBanV1.getFilmComment(mFilmDetail.id)
        }.set {
            mRecyclerView.adapter = FilmCommentAdapter(mContext, it, this)
            checkEmptyAdapter()
        }.end {
            ShowSwipe(false)
        }
    }

}