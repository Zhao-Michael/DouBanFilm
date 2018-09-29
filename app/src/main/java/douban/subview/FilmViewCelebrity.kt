package douban.subview

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import douban.FilmDetail
import douban.adapter.FilmCelebrityAdapter
import org.jetbrains.anko.find
import venerealulcer.R

class FilmViewCelebrity(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_celebrity_layout
    private val mFilmDetail = filmDetail

    init {
        initRecyclerView(mFilmDetail)
    }

    private fun initRecyclerView(film: FilmDetail) {
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(mContext, 1)
        mRecyclerView.adapter = FilmCelebrityAdapter(mContext, film)
    }

}