package douban.subview

import android.content.Context
import douban.FilmDetail
import douban.adapter.FilmCelebrityAdapter
import michaelzhao.R

class FilmCelebrityView(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmDetail = filmDetail

    init {
        initRecyclerView()
        initAdapter()
    }

    override fun initAdapter() {
        mRecyclerView.adapter = FilmCelebrityAdapter(mContext, mFilmDetail, this)
        checkEmptyAdapter()
        showSwipe(false)
    }

}