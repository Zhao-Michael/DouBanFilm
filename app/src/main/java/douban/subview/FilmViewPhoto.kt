package douban.subview

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.widget.TextView
import douban.FilmDetail
import douban.adapter.FilmPhotosAdapter
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor
import util.OnClick
import util.uiThread
import venerealulcer.BaseActivity
import venerealulcer.R

class FilmViewPhoto(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmDetail = filmDetail


    init {
        initSwitchBtn()
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(mContext, 3)
    }

    private fun showFromFilmDetail() {
        mRecyclerView.adapter = FilmPhotosAdapter(mContext, mFilmDetail)
    }

    override fun onNormalClick() {
        showFromFilmDetail()
    }

    override fun onMoreClick() {
        showFromFilmDetail()
    }

}