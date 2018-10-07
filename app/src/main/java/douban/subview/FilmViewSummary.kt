package douban.subview

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.CardView
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import co.lujun.androidtagview.TagContainerLayout
import co.lujun.androidtagview.TagView
import com.daimajia.numberprogressbar.NumberProgressBar
import com.hedgehog.ratingbar.RatingBar
import douban.DouBanV1
import douban.FilmDetail
import douban.adapter.FilmListAdapter
import michaelzhao.R
import org.jetbrains.anko.find
import util.Rx

class FilmViewSummary(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_summary_layout
    private val mFilmDetail = filmDetail
    private val mTextBrief by lazy { mView.find<TextView>(R.id.mTextBrief) }
    private val mTagContainer by lazy { mView.find<TagContainerLayout>(R.id.layout_tagcontainer) }
    private val mTagCardView by lazy { mView.find<CardView>(R.id.cardview_tag) }

    init {
        initSummary()
        initFilmBrief()
        initFilmRate()
        initFilmTag()
    }

    private fun initFilmBrief() {
        FilmListAdapter.ViewHolder(mView).setFilmDetail(mFilmDetail)
    }

    private fun initSummary() {
        mTextBrief.text = "电影：${mFilmDetail.summary.trim()}"
    }

    private fun getTextView(id: Int): TextView {
        return mView.find(id)
    }

    private fun initFilmRate() {
        val film = mFilmDetail
        getTextView(R.id.text_rate).text = film.rating.average.toString()
        getTextView(R.id.text_rate_descript).text = "${film.ratings_count}" + " 人评分"
        mView.find<RatingBar>(R.id.rate_bar).setStar((film.rating.stars.toInt() / 10.0).toFloat())

        fun getProgressBar(id: Int): NumberProgressBar = mView.find(id)
        val sum = film.rating.details.sum()
        getProgressBar(R.id.rate_progress_1).progress = (film.rating.details.star1 / sum * 100).toInt()
        getProgressBar(R.id.rate_progress_2).progress = (film.rating.details.star2 / sum * 100).toInt()
        getProgressBar(R.id.rate_progress_3).progress = (film.rating.details.star3 / sum * 100).toInt()
        getProgressBar(R.id.rate_progress_4).progress = (film.rating.details.star4 / sum * 100).toInt()
        getProgressBar(R.id.rate_progress_5).progress = (film.rating.details.star5 / sum * 100).toInt()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initFilmTag() {
        mTagContainer.setOnTouchListener { _: View, motionEvent: MotionEvent ->
            mTagCardView.onTouchEvent(motionEvent)
            return@setOnTouchListener false
        }

        mTagContainer.setOnTagClickListener(object : TagView.OnTagClickListener {
            override fun onTagLongClick(position: Int, text: String?) = Unit

            override fun onTagCrossClick(position: Int) = Unit

            override fun onTagClick(position: Int, text: String?) {
                Rx.get {
                    DouBanV1.getTagFilmList(text.toString())
                }.set {
                    println(it)
                    //TODO
                }
            }
        })

        if (mFilmDetail.tags.isNotEmpty()) {
            mTagContainer.visibility = View.VISIBLE
            mTagContainer.tags = mFilmDetail.tags
        }
    }

}