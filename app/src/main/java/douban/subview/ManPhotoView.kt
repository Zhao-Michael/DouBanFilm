package douban.subview

import android.content.Context
import douban.DouBanV1
import douban.FilmMan
import douban.adapter.FilmPhotoAdapter
import michaelzhao.R
import util.Rx
import kotlin.math.abs
import kotlin.math.max

//影人图片
class ManPhotoView(context: Context, filmMan: FilmMan) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmMan = filmMan

    init {
        initRecyclerView(3)
        initAdapter()
    }

    override fun initAdapter() {
        ShowSwipe()
        Rx.get {
            DouBanV1.getFilmManPhoto(mFilmMan.id, 1)
        }.set {
            mRecyclerView.adapter = FilmPhotoAdapter(mRecyclerView, it)
            checkEmptyAdapter()
        }.end {
            ShowSwipe(false)
        }
    }

    override fun onLoadMore() {
        ShowSwipe()
        Rx.get {
            DouBanV1.getFilmManPhoto(mFilmMan.id, 1)
        }.set {
            mRecyclerView.adapter = FilmPhotoAdapter(mRecyclerView, it)
            checkEmptyAdapter()
        }.end {
            ShowSwipe(false)
        }
    }

}