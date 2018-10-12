package douban.subview

import android.content.Context
import douban.DouBanV1
import douban.FilmList
import douban.FilmMan
import douban.adapter.FilmListAdapter
import michaelzhao.R
import util.Rx
import kotlin.math.abs
import kotlin.math.max

class ManWorkView(context: Context, filmMan: FilmMan) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmMan = filmMan

    init {
        initRecyclerView()
        onSwitchPage(1)
        ShowSwitcherLayout(false)
        initPageSwitch()
        ShowPageSwitch(true)
    }

    override fun onSwitchPage(page: Int) {
        EnablePageSwitch(false)
        ShowSwipe()
        Rx.get {
            DouBanV1.getFilmManWork(mFilmMan.id, abs(page - 1) * mItemCount)
        }.set {
            it.works.map { it.subject }
            val filmList = FilmList(it.count, it.start, it.total, it.works.map { it.subject }, "")
            setTotalPage(max(it.total / mItemCount + 1, 1))
            mRecyclerView.adapter = FilmListAdapter(mContext, filmList)
        }.end {
            EnablePageSwitch()
            ShowSwipe(false)
        }
    }

}