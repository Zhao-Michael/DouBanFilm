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
class FilmManPhotos(context: Context, filmMan: FilmMan) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmMan = filmMan

    init {
        initRecyclerView(3)
        onSwitchPage(1)
        ShowSwitcherLayout(false)
        ShowPageSwitch(true)
        initPageSwitch()
    }

    override fun onSwitchPage(page: Int) {
        EnablePageSwitch(false)
        ShowSwipe()
        Rx.get {
            DouBanV1.getFilmManPhoto(mFilmMan.id, abs(page - 1) * 30)
        }.set {
            setTotalPage(max(it.total / 30 + 1, 1))
            mRecyclerView.adapter = FilmPhotoAdapter(mRecyclerView, it)
        }.end {
            EnablePageSwitch()
            ShowSwipe(false)
        }
    }

}