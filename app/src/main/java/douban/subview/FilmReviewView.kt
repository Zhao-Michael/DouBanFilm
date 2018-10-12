package douban.subview

import android.content.Context
import douban.DouBanV1
import douban.FilmDetail
import douban.adapter.FilmPopReviewAdapter
import douban.adapter.FilmReviewAdapter
import michaelzhao.R
import util.Rx
import kotlin.math.abs
import kotlin.math.max

class FilmReviewView(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_common_subview_layout
    private val mFilmDetail = filmDetail

    init {
        initRecyclerView()
        initSwipeLayout()
        initSwitchBtn()
        initPageSwitch()
    }

    override fun onNormalClick() {
        ShowPageSwitch(false)
        mRecyclerView.adapter = FilmPopReviewAdapter(mContext, mFilmDetail)
    }

    override fun onMoreClick() {
        ShowPageSwitch()
        onSwitchPage(currPage)
    }

    override fun onSwitchPage(page: Int) {
        EnablePageSwitch(false)
        ShowSwipe()
        Rx.get {
            DouBanV1.getFilmReview(mFilmDetail.id, abs(page - 1) * mItemCount)
        }.set {
            setTotalPage(max(it.total / mItemCount + 1, 1))
            mRecyclerView.adapter = FilmReviewAdapter(mContext, it)
        }.end {
            EnablePageSwitch()
            ShowSwipe(false)
        }
    }


}