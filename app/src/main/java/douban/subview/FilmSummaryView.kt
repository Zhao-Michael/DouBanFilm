package douban.subview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.widget.CardView
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.cunoraz.tagview.Tag
import com.cunoraz.tagview.TagView
import com.daimajia.numberprogressbar.NumberProgressBar
import douban.FilmDetail
import douban.adapter.FilmListAdapter
import me.zhanghai.android.materialratingbar.MaterialRatingBar
import michaelzhao.R
import michaelzhao.TagFilmActivity
import org.jetbrains.anko.find

class FilmSummaryView(context: Context, filmDetail: FilmDetail) : IFilmView(context) {

    override val mLayout: Int = R.layout.film_summary_layout
    private val mFilmDetail = filmDetail
    private val mTextBrief by lazy { mView.find<TextView>(R.id.mTextBrief) }
    private val mTagContainer by lazy { mView.find<TagView>(R.id.layout_tagcontainer) }
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
        mTextBrief.isSelected = true
        mTextBrief.text = mFilmDetail.summary.trim()
    }

    private fun getTextView(id: Int): TextView {
        return mView.find(id)
    }

    private fun initFilmRate() {
        val film = mFilmDetail
        getTextView(R.id.text_rate).text = film.rating.average.toString()
        getTextView(R.id.text_rate_descript).text = "${film.ratings_count}" + " 人评分"
        val bar = mView.find<MaterialRatingBar>(R.id.rate_bar)
        bar.max = 100
        bar.progress = Math.round(film.rating.average.toFloat() * 10)
        bar.setIsIndicator(true)
        val color = ColorStateList.valueOf(Color.rgb(0xFF, 0xBB, 0x33))
        bar.supportProgressTintList = color
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

        mTagContainer.setOnTagClickListener { tag, _ -> TagFilmActivity.showTagFilmList(tag!!.text, mFilmDetail.subtype) }

        if (mFilmDetail.tags.isNotEmpty()) {
            mTagContainer.visibility = View.VISIBLE
            val tag = mFilmDetail.tags.map { Tag(it).apply { layoutColor = getRandomColor(); } }
            mTagContainer.addTags(tag)
        }
    }


    private fun getRandomColor(): Int {
        val RED = "F44336";
        val LIGHTBLUE = "03A9F4";
        val AMBER = "FFC107";
        val ORANGE = "FF9800";
        val YELLOW = "FFEB3B";
        val LIME = "CDDC39";
        val BLUE = "2196F3";
        val INDIGO = "3F51B5";
        val LIGHTGREEN = "8BC34A";
        val GREY = "9E9E9E";
        val DEEPPURPLE = "673AB7";
        val TEAL = "009688";
        val CYAN = "00BCD4";

        val BG_COLOR_ALPHA = "99"

        val COLORS = arrayOf(RED, LIGHTBLUE, AMBER, ORANGE, YELLOW, LIME, BLUE, INDIGO, LIGHTGREEN, GREY, DEEPPURPLE, TEAL, CYAN)
        val random = (Math.random() * COLORS.size)
        return Color.parseColor("#" + BG_COLOR_ALPHA + COLORS[random.toInt()])
    }

}