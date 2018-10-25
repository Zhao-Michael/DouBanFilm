package douban.subview

import android.content.Context
import douban.DouBanV1
import douban.FilmDetail
import douban.adapter.FilmPhotoAdapter
import michaelzhao.R
import util.Rx

//电影剧照
class FilmPhotoView(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmDetail = filmDetail

    init {
        initRecyclerView(2)
        initAdapter()
    }

    override fun initAdapter() {
        showSwipe()
        Rx.get {
            DouBanV1.getFilmPhoto(mFilmDetail.id, 1)
        }.set {
            initRecyclerView(3)
            mRecyclerView.adapter = FilmPhotoAdapter(mRecyclerView, it, this)
            checkEmptyAdapter()
        }.end {
            showSwipe(false)
        }
    }

}